package com.theater.app.repository;

import com.theater.app.domain.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    @Query(value = "select order_date, order_total, id, SUM(order_total) as total from user_order group by month (order_date)", nativeQuery = true)
    List<Order> find();
}
