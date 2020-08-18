package com.theater.app.domain;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
public class RepertoireToCartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "repertoire_id")
    private Repertoire repertoire;

    @ManyToOne
    @JoinColumn
    private CartItem cartItem;

}
