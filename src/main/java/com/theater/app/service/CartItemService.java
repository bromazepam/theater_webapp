package com.theater.app.service;

import com.theater.app.domain.*;

import java.util.List;

public interface CartItemService {
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

    CartItem updateCartItem(CartItem cartItem);

    CartItem addRepertoireToCartItem(Repertoire repertoire, User user, int qty);

    void removeCartItem(CartItem cartItem);

    CartItem findById(Long id);

    CartItem save(CartItem cartItem);

    List<CartItem> findByOrder(Order order);
}
