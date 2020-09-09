package com.theater.app.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Document
public class Repertoire {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

//
    @DBRef
    private Stage stage;

//    @ManyToOne(optional = false)
//    @JoinColumn(name="id_play")
    @DBRef
    private Play play;

    private boolean status;
    private Date timestamp;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date projectionDate;

//    @Temporal(TemporalType.TIME)
//    @DateTimeFormat(pattern = "hh:mm:ss")
    private String projectionTime;

    @Field(name = "projection_datetime")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd.MM.yyyy hh:mm")
    private Date projection_datetime;

    private int price;
    private int availableSeats;

//    @OneToMany(mappedBy = "repertoire")
//    @JsonIgnore
    @DBRef
    private List<RepertoireToCartItem> repertoireToCartItemList;

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Play getPlay() {
        return play;
    }

    public void setPlay(Play play) {
        this.play = play;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

//    @Column(name="timestamp", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Field(name = "timestamp")
    public Date getTimestamp() {
        return timestamp;
    }
    @PrePersist
    protected void onCreate() {
        timestamp = new Date();
    }
    @PreUpdate
    protected void onUpdate() {
        timestamp = new Date();
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getProjectionDate() {
        return projectionDate;
    }

    public void setProjectionDate(Date projectionDate) {
        this.projectionDate = projectionDate;
    }

    public String getProjectionTime() {
        return projectionTime;
    }

    public void setProjectionTime(String projectionTime) {
        this.projectionTime = projectionTime;
    }

    public Date getProjection_datetime() {
        return projection_datetime;
    }

    public void setProjection_datetime(Date projection_datetime) {
        this.projection_datetime = projection_datetime;
    }
}
