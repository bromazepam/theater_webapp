package com.theater.app.service.impl;

import com.theater.app.domain.*;
import com.theater.app.repository.CartItemRepository;
import com.theater.app.repository.RepertoireToCartItemRepository;
import com.theater.app.service.CartItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final RepertoireToCartItemRepository repertoireToCartItemRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, RepertoireToCartItemRepository repertoireToCartItemRepository) {
        this.cartItemRepository = cartItemRepository;
        this.repertoireToCartItemRepository = repertoireToCartItemRepository;
    }

    public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart){
        return cartItemRepository.findByShoppingCart(shoppingCart);
    }

    @Override
    public CartItem updateCartItem(CartItem cartItem) {
        return null;
    }

    @Override
    public CartItem addRepertoireToCartItem(Repertoire repertoire, User user, int qty) {
        return null;
    }

    @Override
    public void removeCartItem(CartItem cartItem) {

    }

    @Override
    public CartItem findById(Long id) {
        return null;
    }

    @Override
    public CartItem save(CartItem cartItem) {
        return null;
    }

    @Override
    public List<CartItem> findByOrder(Order order) {
        return null;
    }
}
