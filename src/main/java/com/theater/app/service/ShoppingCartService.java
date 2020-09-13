package com.theater.app.service;

import com.theater.app.domain.ShoppingCart;
import com.theater.app.domain.User;

public interface ShoppingCartService {
    ShoppingCart updateShoppingCart(ShoppingCart shoppingCart, User user);

    void clearShoppingCart(ShoppingCart shoppingCart, User user);
}
