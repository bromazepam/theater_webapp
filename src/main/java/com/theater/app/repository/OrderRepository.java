package com.theater.app.repository;

import com.theater.app.domain.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {
    @Query(value = "select order_date, SUM(order_total) as total from user_order group by month(order_date)", nativeQuery = true)
    Optional<List<Object[]>> getAggregates();
}
