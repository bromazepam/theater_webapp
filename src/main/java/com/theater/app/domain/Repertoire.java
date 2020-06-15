package com.theater.app.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Repertoire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="id_stage")
    private Stage stage;

    @ManyToOne(optional = false)
    @JoinColumn(name="id_play")
    private Play play;

    private boolean status;
    private Date timestamp;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date projectionDate;

    @Temporal(TemporalType.TIME)
//    @DateTimeFormat(pattern = "HH:mm")
    private Date projectionTime;

    @Column(name = "projection_datetime")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd.MM.yyyy hh:mm")
    private Date projection_datetime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Column(name="timestamp", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
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

    public Date getProjectionTime() {
        return projectionTime;
    }

    public void setProjectionTime(Date projectionTime) {
        this.projectionTime = projectionTime;
    }

    public Date getProjection_datetime() {
        return projection_datetime;
    }

    public void setProjection_datetime(Date projection_datetime) {
        this.projection_datetime = projection_datetime;
    }
}
