package com.cardboard.dto;

import java.time.OffsetDateTime;

public record CardDetailsDto(
        Long id,
        boolean blocked,
        OffsetDateTime blockedAt,
        String blockReason,
        int blocsAmount,
        Long columnId,
        String columnName
        ) {


}
