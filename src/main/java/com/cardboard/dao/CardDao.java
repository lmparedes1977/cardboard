package com.cardboard.dao;

import com.cardboard.dto.CardDetailsDto;
import lombok.AllArgsConstructor;

import java.sql.Connection;

@AllArgsConstructor
public class CardDao {

    private final Connection connection;

    public CardDetailsDto findById() {
        return null;
    }
}
