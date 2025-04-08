package com.cardboard.ui;

import com.cardboard.persistence.entity.BoardColumnEntity;
import com.cardboard.persistence.entity.BoardEntity;
import com.cardboard.service.BoardColumnQueryService;
import com.cardboard.service.BoardQueryService;
import com.cardboard.service.CardQueryService;
import lombok.AllArgsConstructor;
import lombok.Data;

import static com.cardboard.persistence.config.ConnectionConfig.connect;

import java.sql.SQLException;
import java.util.Scanner;

@Data
@AllArgsConstructor
public class BoardMenu {

    private final BoardEntity entity;
    private final Scanner intScan = new Scanner(System.in);
    private final Scanner stringScan = new Scanner(System.in);
    private final Scanner longScan = new Scanner(System.in);

    public void execute() {
        try {
            System.out.printf("Bem-vindo ao board %s - %d! Seleciona a operação desejada: ", entity.getName(), entity.getId());
            var option = 0;
            do {
                System.out.println("1. Create a new card");
                System.out.println("2. Mover a card");
                System.out.println("3. Block a card");
                System.out.println("4. Unblock a card");
                System.out.println("5. Cancel a card");
                System.out.println("6. View a column");
                System.out.println("7. View columns with cards");
                System.out.println("8. View Card");
                System.out.println("9. Back to Board Menul");
                System.out.println("10. Exit");
                option = intScan.nextInt();
                switch (option) {
                    case 1 -> creatCard();
                    case 2 -> moveCardoToNextColumn();
                    case 3 -> blockCard();
                    case 4 -> unblockCard();
                    case 5 -> cancelCard();
                    case 6 -> showBoard();
                    case 7 -> showColumn();
                    case 8 -> showCard();
                    case 9 -> System.out.println("Backing to board menu...");
                    case 10 -> System.exit(0);
                    default -> System.out.println("Invalid option");
                }
            } while (option != 9);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void creatCard() {
    }

    private void moveCardoToNextColumn() {
    }

    private void blockCard() {
    }

    private void unblockCard() {
    }

    private void cancelCard() {
    }

    private void showBoard() throws SQLException {
        try(var connection = connect()) {
            var optional = new BoardQueryService(connection).ShowBoardDetails(entity.getId());
            optional.ifPresent(b -> {
                        System.out.printf("******* Board %d - %s ********\n", b.id(), b.name());
                        b.columns().forEach(c -> {
                            System.out.printf("Column: %s - type: %s - %d cards.\n", c.name(), c.type().name(), c.quantity());
                        });
                    });
        }
    }

    private void showColumn() throws SQLException {
        System.out.printf("Pick a Column form board %s\n", entity.getName());
        var columnsIds = entity.getBoardsColumns().stream().map(BoardColumnEntity::getId).toList();
        var selectedColumn = -1L;
        while(!columnsIds.contains(selectedColumn)){
            entity.getBoardsColumns().forEach(c -> {
                System.out.printf("%d - %s [%s]\n", c.getId(), c.getName(),c.getType().name());
            });
            selectedColumn = longScan.nextLong();
        }
        try(var connection = connect()) {
            var column = new BoardColumnQueryService(connection).findById(selectedColumn);
            column.ifPresent(co -> {
                System.out.printf("Column %s type %s\n", co.getName(), co.getType().name());
                co.getCards().forEach(ca -> {
                    System.out.printf("Card %s - %s\nDescription: %s", ca.getId(), ca.getTitle(), ca.getDescription());
                });
            });
        }
    }

    private void showCard() throws SQLException {
        System.out.print("Enter the card id you wish to visualize: ");
        var selectedCardId = longScan.nextLong();
        try (var connection = connect()) {
            new CardQueryService(connection)
                    .findById(selectedCardId)
                    .ifPresentOrElse(c -> {
                        System.out.printf("Card %s - %s\nDescription: %s\n", c.id(), c.title(), c.description());
                        System.out.printf(c.blocked() ? "Blocked for reason %s\n" : "Not Blocked\n", c.blockReason());
                        System.out.printf("Blocked %s times\n", c.blocsAmount());
                        System.out.printf("Presently belongs to Column %s - %s",c.columnId(), c.columnName());
                    },
                        () -> System.out.printf("Card with id %d dows not exist.\n", selectedCardId));
        }
    }
}



