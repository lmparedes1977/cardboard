package com.cardboard.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cardboard.dto.BoardColumnDto;
import com.cardboard.entity.CardEntity;
import org.postgresql.jdbc.PgStatement;

import com.cardboard.entity.BoardColumnEntity;
import com.cardboard.entity.BoardColumnTypeEnum;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardColumnDao {

    private final Connection connection;

    public BoardColumnEntity insert(final BoardColumnEntity entity) throws SQLException {
        var sql = "INSERT INTO boards_columns (name, column_order, type, board_id) VALUES (?, ?, ?, ?)";
        connection.setAutoCommit(false);
        try(var statement = connection.prepareStatement(sql, PgStatement.RETURN_GENERATED_KEYS)) {
            var i = 1;
            statement.setString(i++, entity.getName());
            statement.setInt(i++, entity.getColumn_order());
            statement.setString(i++, entity.getType().name());
            statement.setLong(i++, entity.getBoard().getId());
            statement.executeUpdate();
            try(var generatedKeys = statement.getGeneratedKeys()) {
                if(generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                    System.out.println(entity.getId());
                }
            }return entity;
        } catch (SQLException e) {
            throw e;
        }       
    }

    public List<BoardColumnEntity> findByBoardId(Long id) throws SQLException {
        var sql = "SELECT id, name, column_order, type FROM boards_columns WHERE board_id = ? ORDER BY column_order";
        connection.setAutoCommit(false);
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

    public List<BoardColumnDto> findByBoardIDetailed(Long id) throws SQLException {
        var sql = """
                SELECT
                    bc.id,
                    bc.name,
                    bc.type,
                    COUNT(SELECT c.id FROM cards c WHERE c.board_column_id = bc.id) as cards_amount
                FROM boards_columns bc
                WHERE board_id = ?
                ORDER BY bc.column_order
                """;
        connection.setAutoCommit(false);
        try(var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            var columns = new ArrayList<BoardColumnDto>();
            while (resultSet.next()) {
                var dto = new BoardColumnDto(
                        resultSet.getLong("bc.id"),
                        resultSet.getString("bc.name"),
                        BoardColumnTypeEnum.valueOf(resultSet.getString("bc.type")),
                        resultSet.getInt("cards_amount")
                );
                columns.add(dto);
            }
            return columns;
        } catch (SQLException e) {
            throw e;
        }
    }

    public Optional<BoardColumnEntity> findById(Long id) throws SQLException {
        var sql = """
                SELECT bc.name,
                       bc.type,
                       c.id,
                       c.title,
                       c.description
                       FROM boards_columns bc
                       INNER JOIN cards c
                       ON c.board_column_id = bc.id
                       WHERE bc.id = ?
                """;
        connection.setAutoCommit(false);
        try(var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            var entity = new BoardColumnEntity();
            if(resultSet.next()) {
                entity.setName(resultSet.getString("bc.name"));
                entity.setType(BoardColumnTypeEnum.valueOf(resultSet.getString("bc.type")));
            }
            var cardEntities = new ArrayList<CardEntity>();
            while (resultSet.next()) {
                var cardEntity = new CardEntity();
                cardEntity.setId(resultSet.getLong("c.id"));
                cardEntity.setTitle(resultSet.getString("c.title"));
                cardEntity.setDescription(resultSet.getString("c.description"));
                cardEntities.add(cardEntity);
            }
            entity.setCards(cardEntities);
            return Optional.of(entity);
        } catch (SQLException e) {
            throw e;
        }
    }

}

