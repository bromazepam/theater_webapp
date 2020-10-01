package com.theater.app.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderReport {

    private int totalAmount;
    private int month;
    private String date;

    public OrderReport(int totalAmount, int month) {
        this.totalAmount = totalAmount;
        this.month = month;
        setDate(month);
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

    private void setDate(int month){
        switch (month) {
            case 1:  this.date = "January";       break;
            case 2:  this.date = "February";      break;
            case 3:  this.date = "March";         break;
            case 4:  this.date = "April";         break;
            case 5:  this.date = "May";           break;
            case 6:  this.date = "June";          break;
            case 7:  this.date = "July";          break;
            case 8:  this.date = "August";        break;
            case 9:  this.date = "September";     break;
            case 10: this.date = "October";       break;
            case 11: this.date = "November";      break;
            case 12: this.date = "December";      break;
            default: this.date = "Invalid month"; break;
        }
    }

    public String getDate() {
        return date;
    }
}
