package com.theater.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

//@Document
public class CartItem {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private String id;
    private int qty;
    private int subtotal;

//    @OneToOne
//    @DBRef
    private Repertoire repertoire;

//    @OneToMany(mappedBy = "cartItem")
//    @DBRef
//    @JsonIgnore
//    private List<RepertoireToCartItem> repertoireToCartItemList = new ArrayList<>();

//    @ManyToOne
//    @JoinColumn(name = "shopping_cart_id")
//    @DBRef
//    private ShoppingCart shoppingCart;

//    @ManyToOne
//    @JoinColumn(name = "order_id")
//    @DBRef
//    private Order order;

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public Repertoire getRepertoire() {
        return repertoire;
    }

    public void setRepertoire(Repertoire repertoire) {
        this.repertoire = repertoire;
    }

//    public List<RepertoireToCartItem> getRepertoireToCartItemList() {
//        return repertoireToCartItemList;
//    }
//
//    public void setRepertoireToCartItemList(List<RepertoireToCartItem> repertoireToCartItemList) {
//        this.repertoireToCartItemList = repertoireToCartItemList;
//    }

//    public ShoppingCart getShoppingCart() {
//        return shoppingCart;
//    }
//
//    public void setShoppingCart(ShoppingCart shoppingCart) {
//        this.shoppingCart = shoppingCart;
//    }

//    public Order getOrder() {
//        return order;
//    }
//
//    public void setOrder(Order order) {
//        this.order = order;
//    }
}
