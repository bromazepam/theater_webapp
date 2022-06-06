package com.theater.app.domain.reportDAO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderReport {

    private int totalAmount;
    private int month;
    private String date;

    public OrderReport(int totalAmount, int month) {
        this.totalAmount = totalAmount;
        this.month = month;
        setDate(month);
    }

    private void setDate(int month) {
        switch (month) {
            case 1:
                this.date = "Januar";
                break;
            case 2:
                this.date = "Februar";
                break;
            case 3:
                this.date = "Mart";
                break;
            case 4:
                this.date = "April";
                break;
            case 5:
                this.date = "Maj";
                break;
            case 6:
                this.date = "Jun";
                break;
            case 7:
                this.date = "Jul";
                break;
            case 8:
                this.date = "Avgust";
                break;
            case 9:
                this.date = "Septembar";
                break;
            case 10:
                this.date = "Octobar";
                break;
            case 11:
                this.date = "Novembar";
                break;
            case 12:
                this.date = "Decembar";
                break;
            default:
                this.date = "Invalid month";
                break;
        }
    }

}
