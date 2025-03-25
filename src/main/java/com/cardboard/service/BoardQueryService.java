package com.cardboard.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import com.cardboard.dao.BoardColumnDao;
import com.cardboard.dao.BoardDao;
import com.cardboard.entity.BoardEntity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardQueryService {

    private final Connection connection;

    public Optional<BoardEntity> findById(final Long id) throws SQLException {
        var boardDao = new BoardDao(connection);
        var boardColumnDao = new BoardColumnDao(connection);
        var optional = boardDao.findById(id);
        if (optional.isPresent()) {
            var entity = optional.get();
            entity.setBoardColumns(boardColumnDao.findByBoardId(entity.getId()));
        }
        return Optional.empty();
    }
}
