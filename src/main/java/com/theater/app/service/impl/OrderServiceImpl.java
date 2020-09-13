package com.theater.app.service.impl;

import com.theater.app.domain.*;
import com.theater.app.exceptions.NotFoundException;
import com.theater.app.repository.OrderRepository;
import com.theater.app.service.CartItemService;
import com.theater.app.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final CartItemService cartItemService;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(CartItemService cartItemService, OrderRepository orderRepository) {
        this.cartItemService = cartItemService;
        this.orderRepository = orderRepository;
    }

    @Override
    public synchronized Order createOrder(ShoppingCart shoppingCart, Payment payment, User user) {
        Order order = new Order();
        order.setOrderStatus("created");
        order.setPayment(payment);

//        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
        List<CartItem> cartItemList = user.getShoppingCart().getCartItemList();

        for(CartItem cartItem: cartItemList){
            Repertoire repertoire = cartItem.getRepertoire();
//            cartItem.setOrder(order);
            repertoire.setAvailableSeats(repertoire.getAvailableSeats()-cartItem.getQty());
        }

        order.setCartItemList(cartItemList);
        order.setOrderDate(Calendar.getInstance().getTime());
        order.setOrderTotal(shoppingCart.getGrandTotal());
        payment.setOrder(order);
        order.setUser(user);
        order = orderRepository.save(order);

        return order;
    }

    @Override
    public Order findById(String id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (!orderOptional.isPresent()) {
            throw new NotFoundException("order not found");
        }

        return orderOptional.get();
    }
}
