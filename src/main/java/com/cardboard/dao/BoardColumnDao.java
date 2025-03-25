package com.cardboard.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.jdbc.PgStatement;

import com.cardboard.entity.BoardColumnEntity;
import com.cardboard.entity.BoardColumnTypeEnum;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardColumnDao {

    private final Connection connection;

    public BoardColumnEntity insert(final BoardColumnEntity entity) throws SQLException {
        var sql = "INSERT INTO board_column (id, name, column_order, type, board_id) VALUES (?, ?, ?, ?, ?)";
        try(var statement = connection.prepareStatement(sql)) {
            var i = 1;
            statement.setLong(i++, entity.getId());
            statement.setString(i++, entity.getName());
            statement.setInt(i++, entity.getColumn_order());
            statement.setString(i++, entity.getType().name());
            statement.setLong(i++, entity.getBoard().getId());
            statement.executeUpdate();
            if (statement instanceof PgStatement pgStatement) {
                entity.setId(pgStatement.getLastOID());
            }
            return entity;
        } catch (SQLException e) {
            throw e;
        }       
    }

    public List<BoardColumnEntity> findByBoardId(Long id) throws SQLException {
        var sql = "SELECT id, name, column_order, type FROM board_column WHERE board_id = ? ORDER BY column_order";
        try(var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            var entities = new ArrayList<BoardColumnEntity>();
            while (resultSet.next()) {
                var entity = new BoardColumnEntity();
                entity.setId(resultSet.getLong("id"));
                entity.setName(resultSet.getString("name"));
                entity.setColumn_order(resultSet.getInt("column_order"));
                entity.setType(BoardColumnTypeEnum.valueOf(resultSet.getString("type")));
                entities.add(entity);
            }
            return entities;
        } catch (SQLException e) {
            throw e;
        }
    }

}

