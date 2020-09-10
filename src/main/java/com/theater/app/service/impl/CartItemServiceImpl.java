package com.theater.app.service.impl;

import com.theater.app.domain.*;
import com.theater.app.repository.CartItemRepository;
import com.theater.app.repository.RepertoireToCartItemRepository;
import com.theater.app.service.CartItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final RepertoireToCartItemRepository repertoireToCartItemRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, RepertoireToCartItemRepository repertoireToCartItemRepository) {
        this.cartItemRepository = cartItemRepository;
        this.repertoireToCartItemRepository = repertoireToCartItemRepository;
    }

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

    @Override
    public CartItem addRepertoireToCartItem(Repertoire repertoire, User user, int qty) {
        List<CartItem> cartItemList = findByShoppingCart(user.getShoppingCart());

        for (CartItem cartItem : cartItemList) {
            if (repertoire.getId() == cartItem.getRepertoire().getId()) {
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

    @Override
    public void removeCartItem(CartItem cartItem) {
        repertoireToCartItemRepository.deleteByCartItem(cartItem);
        cartItemRepository.delete(cartItem);

    }

    @Override
    public CartItem findById(Long id) {
        Optional<CartItem> cartItem = cartItemRepository.findById(id);
        return cartItem.get();
    }

    @Override
    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public List<CartItem> findByOrder(Order order) {
        return cartItemRepository.findByOrder(order);
    }
}
