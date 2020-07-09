package com.theater.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int qty;
    private int subtotal;

    @OneToOne
    private Repertoire repertoire;

    @OneToMany(mappedBy = "cartItem")
    @JsonIgnore
    private List<RepertoireToCartItem> repertoireToCartItemList;

    @ManyToOne
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<RepertoireToCartItem> getRepertoireToCartItemList() {
        return repertoireToCartItemList;
    }

    public void setRepertoireToCartItemList(List<RepertoireToCartItem> repertoireToCartItemList) {
        this.repertoireToCartItemList = repertoireToCartItemList;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
