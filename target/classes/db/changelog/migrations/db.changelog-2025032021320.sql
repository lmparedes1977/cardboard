-- liquibase formatted sql
-- changeset leonardo:2025032021320
-- comment: boards table create

CREATE TABLE BOARDS(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL
) ;

-- rollback DROP TABLE BOARDS