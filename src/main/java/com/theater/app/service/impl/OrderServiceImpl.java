package com.theater.app.service.impl;

import com.theater.app.domain.*;
import com.theater.app.exceptions.NotFoundException;
import com.theater.app.repository.*;
import com.theater.app.service.CartItemService;
import com.theater.app.service.OrderService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Service
public class OrderServiceImpl implements OrderService {

    private final MongoTemplate mongoTemplate;
    private final CartItemService cartItemService;
    private final OrderRepository orderRepository;
    private final RepertoireRepository repertoireRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(CartItemService cartItemService, OrderRepository orderRepository,
                            RepertoireRepository repertoireRepository, UserRepository userRepository,
                            MongoTemplate mongoTemplate) {
        this.cartItemService = cartItemService;
        this.orderRepository = orderRepository;
        this.repertoireRepository = repertoireRepository;
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public synchronized Order createOrder(ShoppingCart shoppingCart, Payment payment, User user) {
        Order order = new Order();
        order.setOrderStatus("created");
        order.setPayment(payment);

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        for (CartItem cartItem : cartItemList) {
            Repertoire repertoire = cartItem.getRepertoire();
            repertoire.setAvailableSeats(repertoire.getAvailableSeats() - cartItem.getQty());
            repertoireRepository.save(repertoire);
        }

        order.setCartItemList(cartItemList);
        order.setOrderDate(Calendar.getInstance().getTime());
        order.setOrderTotal(shoppingCart.getGrandTotal());
        order = orderRepository.save(order);

        List<Order> orderList = userRepository.findByUsername(user.getUsername()).getOrderList();
        orderList.add(order);
        user.setOrderList(orderList);
        userRepository.save(user);
        return order;
    }

    @Override
    public Order findByOrderId(String id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (!orderOptional.isPresent()) {
            throw new NotFoundException("order not found");
        }

        return orderOptional.get();
    }

    @Override
    public List<OrderReport> findMonthlyProfit() {
        Aggregation aggregation = newAggregation(
                Aggregation.project()
                        .andExpression("$order_date").extractMonth().as("profitMonth"),
                group("order_date")
                        .sum("$order_total").as("profitTotal"));

        return mongoTemplate.aggregate(aggregation, "user_order", OrderReport.class).getMappedResults();
    }
}
