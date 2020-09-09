package com.theater.app.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Document(collection = "user_order")
public class Order {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private Date orderDate;
    private String orderStatus;
    private int orderTotal;

//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @DBRef
    private List<CartItem> cartItemList;

//    @OneToOne(cascade = CascadeType.ALL)
    @DBRef
    private Payment payment;

//    @ManyToOne
    @DBRef
    private User user;

    @Transient
    private int total;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(int orderTotal) {
        this.orderTotal = orderTotal;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
