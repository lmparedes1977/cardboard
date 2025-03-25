package com.cardboard.service;

import com.cardboard.dao.BoardDao;
import com.cardboard.entity.BoardEntity;

import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;

@AllArgsConstructor
public class BoardService {

    private final Connection connection;

    public BoardEntity insert(final BoardEntity entity) throws SQLException {
        var dao = new BoardDao(connection);
        try {
            dao.insert(entity);
            entity.getBoardColumns().stream().forEach(
                boardColumn -> {
                    boardColumn.setBoard(entity);
                    new BoardColumnService(connection).insert(boardColumn);
                }
            );
            connection.commit();
            return entity;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }

    }

    public boolean delete(final Long id) throws SQLException {
        var dao = new BoardDao(connection);
        try {
            if(!dao.exists(id)){
                return false;
            }
            dao.delete(id);
            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

}


