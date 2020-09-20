package com.theater.app.repository;

import com.theater.app.domain.Order;
import com.theater.app.domain.OrderReport;
import com.theater.app.service.OrderService;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String>, OrderService {
//    @Query(value = "select order_date as profitMonth, SUM(order_total) as profitTotal from user_order group by month(order_date)")
//    List<Order> getAggregates();

//    @Query("db.getCollection('user_order').aggregate([\n" +
//            "{\"$project\":{\n" +
//            "    \"orderTotal\" : 1,\n" +
//            "    \"month\":{\"$month\":\"$orderDate\"}\n" +
//            "    }},\n" +
//            "    {\"$group\": {\n" +
//            "        \"_id\": \"$month\" ,\n" +
//            "        \"totalAmount\":{\"$sum\": \"$orderTotal\"}\n" +
//            "        }}\n" +
//            "        ])")
//    List<OrderReport> getMonthlyProfit();
}
