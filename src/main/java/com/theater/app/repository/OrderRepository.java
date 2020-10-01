package com.theater.app.repository;

import com.theater.app.domain.Order;
import com.theater.app.domain.OrderReport;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, String> {
    @Query(value = "select order_date as profitMonth, SUM(order_total) as profitTotal from user_order group by month(order_date)")
    List<Order> getAggregates();

//    @Aggregation("{ $group : { _id : {$month:$orderDate}, totalAmount : { $sum : $orderTotal } } }")
//    AggregationResults<OrderReport> sumProfit();

}
