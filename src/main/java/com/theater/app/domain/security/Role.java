package com.theater.app.domain.security;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Role {

    @Id
    private String roleId;
    private String name;

}
