package com.theater.app.service;

import com.theater.app.domain.*;

import java.util.List;

public interface OrderService {
    Order createOrder(ShoppingCart shoppingCart, Payment payment, User user);

    Order findByOrderId(String id);

    List<OrderReport> findMonthlyProfit();
}
