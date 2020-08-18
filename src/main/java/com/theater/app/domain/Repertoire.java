package com.theater.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
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
    @Column(name="timestamp", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date timestamp;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date projectionDate;

//    @Temporal(TemporalType.TIME)
//    @DateTimeFormat(pattern = "hh:mm:ss")
    private String projectionTime;

    @Column(name = "projection_datetime")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd.MM.yyyy hh:mm")
    private Date projection_datetime;

    private int price;
    private int availableSeats;

    @OneToMany(mappedBy = "repertoire")
    @JsonIgnore
    private List<RepertoireToCartItem> repertoireToCartItemList;

    @PrePersist
    protected void onCreate() {
        timestamp = new Date();
    }
    @PreUpdate
    protected void onUpdate() {
        timestamp = new Date();
    }

}
