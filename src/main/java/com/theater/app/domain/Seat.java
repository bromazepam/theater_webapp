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
public class Seat {

    @Id
    private String id;
    private String name;
    private boolean reserved = false;

    @DBRef(lazy = true)
    private Stage stage;

}
