package com.theater.app.service.impl;

import com.theater.app.domain.*;
import com.theater.app.exceptions.NotFoundException;
import com.theater.app.repository.CartItemRepository;
import com.theater.app.repository.RepertoireToCartItemRepository;
import com.theater.app.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final RepertoireToCartItemRepository repertoireToCartItemRepository;

    public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
        return cartItemRepository.findByShoppingCart(shoppingCart);
    }

    @Override
    public CartItem updateCartItem(CartItem cartItem) {
        int total = cartItem.getRepertoire().getPrice() * cartItem.getQty();
        cartItem.setSubtotal(total);

        cartItemRepository.save(cartItem);
        return cartItem;
    }

    @Transactional
    @Override
    public CartItem addRepertoireToCartItem(Repertoire repertoire, User user, int qty) {
        List<CartItem> cartItemList = findByShoppingCart(user.getShoppingCart());

        for (CartItem cartItem : cartItemList) {
            if (repertoire.getId().equals(cartItem.getRepertoire().getId())) {
                cartItem.setQty(cartItem.getQty() + qty);
                cartItem.setSubtotal(repertoire.getPrice() * qty);
                cartItemRepository.save(cartItem);
                return cartItem;
            }
        }

        CartItem cartItem = new CartItem();
        cartItem.setShoppingCart(user.getShoppingCart());
        cartItem.setRepertoire(repertoire);

        cartItem.setQty(qty);
        cartItem.setSubtotal(repertoire.getPrice() * qty);
        cartItem = cartItemRepository.save(cartItem);

        RepertoireToCartItem repertoireToCartItem = new RepertoireToCartItem();
        repertoireToCartItem.setRepertoire(repertoire);
        repertoireToCartItem.setCartItem(cartItem);
        repertoireToCartItemRepository.save(repertoireToCartItem);

        return cartItem;
    }

    @Transactional
    @Override
    public void removeCartItem(CartItem cartItem) {
        repertoireToCartItemRepository.deleteByCartItem(cartItem);
        cartItemRepository.delete(cartItem);

    }

    @Override
    public CartItem findById(String id) {
        Optional<CartItem> cartItem = cartItemRepository.findById(id);
        if (!cartItem.isPresent()) {
            throw new NotFoundException("cart item not found, For ID value: " + id);
        }
        return cartItem.get();
    }

    @Override
    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

}
