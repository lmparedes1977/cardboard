package com.cardboard.dto;

import java.util.List;

public record BoardDetailsDto(
        Long id,
        String name,
        List<BoardColumnDto> columns
) {
}
