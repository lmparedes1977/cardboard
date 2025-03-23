package com.cardboard.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CardEntity {

    private Long id;
    private String title;
    private String description;

}
