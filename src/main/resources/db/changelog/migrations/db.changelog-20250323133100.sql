-- liquibase formatted sql
-- changeset leonardo:20250323133100
-- comment: boards table create

CREATE TABLE BLOCKS(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    blocked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    bock_reason VARCHAR(255) NOT NULL,
    unblocked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    unblock_reason VARCHAR(255),
    type VARCHAR(7),
    card_id BIGINT NOT NULL,
    CONSTRAINT cards__blocks_fk FOREIGN KEY (card_id) REFERENCES CARDS(id) ON DELETE CASCADE
) ;

-- rollback DROP TABLE BOARDS