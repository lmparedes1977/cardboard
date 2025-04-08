package com.cardboard.persistence.dao;

import com.cardboard.dto.CardDetailsDto;
import com.cardboard.persistence.entity.CardEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static com.cardboard.persistence.converter.OffsetDateTimeConverter.toOffsetDateTime;
import static java.util.Objects.nonNull;

@AllArgsConstructor
public class CardDao {

    private final Connection connection;

    public CardEntity insert(CardEntity entity) throws SQLException {
        var sql = "insert into cards (title, description, board_column_id) values (?,?,?);";
        connection.setAutoCommit(false);
        try (var statement = connection.prepareStatement(sql)) {
            var i = 1;
            statement.setString(i++, entity.getTitle());
            statement.setString(i++, entity.getDescription());
            statement.setLong(i++, entity.getBoardColumn().getId());
            try (var generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
        }
        return entity;
    }

    public Optional<CardDetailsDto> findById(Long id) throws SQLException {
        var sql = """
                select c.id,
                       c.title,
                       c.description,
                       b.blocked_at,
                       b.block_reason,
                       b.board_column_id
                       bc.name,
                       (select count(sub_b.id) from blocks sub_b where sub_b.card_id = c.id) as blocks_amount
                       from cards c
                   LEFT JOIN blocs b
                       on c.id = b.card_id
                       and b.unblocked_at is null
                   INNER join boards_columns bc
                       on bc.id = c.board_column_id
                   where c.id - ?;
                """;
        try(var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            if (resultSet.next()) {
                var dto = new CardDetailsDto(
                        resultSet.getLong("c.id"),
                        resultSet.getString("c.title"),
                        resultSet.getString("c.description"),
                        nonNull(resultSet.getString("b.block_reason")),
                        toOffsetDateTime(resultSet.getTimestamp("b.blocked_at")),
                        resultSet.getString("b.block_reason"),
                        resultSet.getInt("blocks_amount"),
                        resultSet.getLong("c.board_column_id"),
                        resultSet.getString("bc.name")
                );
                return Optional.of(dto);
            }
        }
        return Optional.empty();
    }
}
