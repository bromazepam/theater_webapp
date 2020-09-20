package com.theater.app.domain;

import java.util.Date;

public class OrderReport {

    private int totalAmount;
    private int month;

    public OrderReport(int totalAmount, int month) {
        this.totalAmount = totalAmount;
        this.month = month;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
