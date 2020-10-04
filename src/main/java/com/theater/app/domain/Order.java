package com.theater.app.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user_order")
public class Order {

    @Id
    private String id;
    private Date orderDate;
    private String orderStatus;
    private int orderTotal;

    private List<CartItem> cartItemList = new ArrayList<>();

    private Payment payment;

    @Transient
    private int total;

}
