package com.theater.app.service;

import com.theater.app.domain.CartItem;
import com.theater.app.domain.Repertoire;
import com.theater.app.domain.ShoppingCart;
import com.theater.app.domain.User;

import java.util.List;

public interface CartItemService {
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

    CartItem updateCartItem(CartItem cartItem);

    CartItem addRepertoireToCartItem(Repertoire repertoire, User user, int qty);

    void removeCartItem(CartItem cartItem);

    CartItem findById(String id);

    CartItem save(CartItem cartItem);

}
