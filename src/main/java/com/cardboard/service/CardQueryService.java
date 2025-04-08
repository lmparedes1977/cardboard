package com.cardboard.service;

import com.cardboard.dto.CardDetailsDto;
import com.cardboard.persistence.dao.CardDao;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class CardQueryService {

    private final Connection connection;

    public Optional<CardDetailsDto> findById(Long id) throws SQLException {
        var dao = new CardDao(connection);
        return dao.findById(id);
    }
}
