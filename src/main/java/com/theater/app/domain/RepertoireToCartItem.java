package com.theater.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class RepertoireToCartItem {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

//    @ManyToOne
//    @JoinColumn(name = "repertoire_id")
    @DBRef
    private Repertoire repertoire;

//    @ManyToOne
//    @JoinColumn
    @DBRef
    private CartItem cartItem;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Repertoire getRepertoire() {
        return repertoire;
    }

    public void setRepertoire(Repertoire repertoire) {
        this.repertoire = repertoire;
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }
}
