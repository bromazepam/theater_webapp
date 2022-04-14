package com.theater.app.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class ShoppingCart {

    @Id
    private String id;
    private int grandTotal;

    private List<CartItem> cartItemList = new ArrayList<>();

    @DBRef
    private User user;

}
