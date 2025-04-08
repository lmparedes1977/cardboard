package com.cardboard.dto;

import java.time.OffsetDateTime;

public record CardDetailsDto(
        Long id,
        String title,
        String description,
        boolean blocked,
        OffsetDateTime blockedAt,
        String blockReason,
        int blocsAmount,
        Long columnId,
        String columnName
        ) {}
