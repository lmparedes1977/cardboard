package com.cardboard.dto;

import com.cardboard.entity.BoardColumnTypeEnum;

public record BoardColumnDto(
        Long id,
        String name,
        BoardColumnTypeEnum type,
        int quantity
){}