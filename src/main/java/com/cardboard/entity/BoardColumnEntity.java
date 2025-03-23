package com.cardboard.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardColumnEntity {

    private Long id;
    private String name;
    private int column_order;
    private BoardColumnTypeEnum type;

}
