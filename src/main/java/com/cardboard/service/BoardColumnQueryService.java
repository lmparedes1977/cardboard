package com.cardboard.service;

import com.cardboard.dao.BoardColumnDao;
import com.cardboard.entity.BoardColumnEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class BoardColumnQueryService {

    private final Connection connection;

    public Optional<BoardColumnEntity> findById(final Long id) throws SQLException {
        var dao = new BoardColumnDao(connection);
        return dao.findById(id);
    }
}
