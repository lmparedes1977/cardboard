package com.cardboard.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.cardboard.persistence.dao.BoardColumnDao;
import com.cardboard.persistence.entity.BoardColumnEntity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardColumnService {

    private final Connection connection;

    public BoardColumnEntity insert(final BoardColumnEntity entity) {
        var dao = new BoardColumnDao(connection);
        try {
            return dao.insert(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }        
    }
}
