package com.cardboard.service;

import com.cardboard.dao.BoardDao;
import com.cardboard.entity.BoardColumnEntity;
import com.cardboard.entity.BoardEntity;

import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;

@AllArgsConstructor
public class BoardService {

    private final Connection connection;

    public BoardEntity insert(BoardEntity entity) throws SQLException {
        var dao = new BoardDao(connection);
        try {
            dao.insert(entity);
            for (BoardColumnEntity boardColumn : entity.getBoardsColumns()) {
                boardColumn.setBoard(entity);
                new BoardColumnService(connection).insert(boardColumn);
            }
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


