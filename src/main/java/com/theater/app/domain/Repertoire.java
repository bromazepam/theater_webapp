package com.theater.app.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Repertoire {

    @Id
    private String id;
    private Stage stage;

    @DBRef
    private Play play;

    private boolean status;
    private Date timestamp;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date projectionDate;

    @DateTimeFormat(pattern = "hh:mm:ss")
    private String projectionTime;

    @Field(name = "projection_datetime")
    @DateTimeFormat(pattern = "dd.MM.yyyy hh:mm")
    private Date projection_datetime;

    private int price;
    private int availableSeats;


    protected void onCreate() {
        timestamp = new Date();
    }

    protected void onUpdate() {
        timestamp = new Date();
    }

    public boolean isStatus() {
        return status;
    }

}
