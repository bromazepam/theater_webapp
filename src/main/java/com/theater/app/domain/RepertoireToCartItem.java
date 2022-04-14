package com.theater.app.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class RepertoireToCartItem {

    @Id
    private String id;

    @DBRef
    private Repertoire repertoire;

    @DBRef
    private CartItem cartItem;

}
