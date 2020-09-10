package com.theater.app.repository;

import com.theater.app.domain.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, String> {
    @Query(value = "select order_date as profitMonth, SUM(order_total) as profitTotal from user_order group by month(order_date)", nativeQuery = true)
    List<Order> getAggregates();
}
