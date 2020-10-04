package com.theater.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class CartItem {
    @Id
    private String id;
    private int qty;
    private int subtotal;

    private Repertoire repertoire;

    @JsonIgnore
    private List<RepertoireToCartItem> repertoireToCartItemList = new ArrayList<>();

    @DBRef
    private ShoppingCart shoppingCart;

}
