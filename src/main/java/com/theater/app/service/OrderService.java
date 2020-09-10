package com.theater.app.service;

import com.theater.app.domain.Order;
import com.theater.app.domain.Payment;
import com.theater.app.domain.ShoppingCart;
import com.theater.app.domain.User;

public interface OrderService {
    Order createOrder(ShoppingCart shoppingCart, Payment payment, User user);

    Order findById(Long id);
}
