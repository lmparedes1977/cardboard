package com.cardboard.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import com.cardboard.persistence.dao.BoardColumnDao;
import com.cardboard.persistence.dao.BoardDao;
import com.cardboard.dto.BoardDetailsDto;
import com.cardboard.persistence.entity.BoardEntity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardQueryService {

    private final Connection connection;

    public Optional<BoardEntity> findById(final Long id) throws SQLException {
        connection.setAutoCommit(false);
        var boardDao = new BoardDao(connection);
        var boardColumnDao = new BoardColumnDao(connection);
        var optional = boardDao.findById(id);
        if (optional.isPresent()) {
            var entity = optional.get();
            entity.setBoardsColumns(boardColumnDao.findByBoardId(entity.getId()));
            return Optional.of(entity);
        }
        return Optional.empty();
    }


    public Optional<BoardDetailsDto> ShowBoardDetails(final Long id) throws SQLException {
        connection.setAutoCommit(false);
        var boardDao = new BoardDao(connection);
        var boardColumnDao = new BoardColumnDao(connection);
        var optional = boardDao.findById(id);
        if (optional.isPresent()) {
            var entity = optional.get();
            var columnsDto = boardColumnDao.findByBoardIDetailed(entity.getId());
            var board = new BoardDetailsDto(
                    entity.getId(),
                    entity.getName(),
                    columnsDto
            );
            return Optional.of(board);
        }
        return Optional.empty();
    }

}
