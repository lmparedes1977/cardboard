-- liquibase formatted sql
-- changeset leonardo:20250323131600
-- comment: boards table create

CREATE TABLE BOARDS_COLUMNS(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    column_order INT NOT NULL,
    type VARCHAR(7),
    board_id BIGINT NOT NULL,
    CONSTRAINT boards__boards_column_fk FOREIGN KEY (board_id) REFERENCES BOARDS(id) ON DELETE CASCADE,
    CONSTRAINT id_column_order_uk UNIQUE (board_id, column_order)
) ;

-- rollback DROP TABLE BOARDS