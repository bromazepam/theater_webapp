package com.theater.app.service;

import com.theater.app.domain.Order;
import com.theater.app.domain.Payment;
import com.theater.app.domain.ShoppingCart;
import com.theater.app.domain.User;
import com.theater.app.domain.reportDAO.OrderReport;

import java.util.List;

public interface OrderService {
    Order createOrder(ShoppingCart shoppingCart, Payment payment, User user);

    Order findById(String id);

    List<OrderReport> findOrders();
}
