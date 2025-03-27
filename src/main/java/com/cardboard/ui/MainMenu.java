package com.cardboard.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.cardboard.entity.BoardColumnEntity;
import com.cardboard.entity.BoardColumnTypeEnum;
import com.cardboard.entity.BoardEntity;
import com.cardboard.service.BoardColumnService;
import com.cardboard.service.BoardService;
import lombok.AllArgsConstructor;

import static com.cardboard.persistence.config.ConnectionConfig.connect;

public class MainMenu {

    private final Scanner scan = new Scanner(System.in);

    public void execute() {
        var option = 0;
        do {
            System.out.println("Welcome to Cardboard!");
            System.out.println("1. Create a new board");
            System.out.println("2. View a board");
            System.out.println("3. Delete a board");
            System.out.println("4. Exit");
            option = scan.nextInt();
            switch (option) {
                case 1:
                    creatBoard();
                    break;
                case 2:
                    viewBoard();
                    break;
                case 3:
                    deleteBoard();
                    break;
                case 4:
                    exit();
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } while (option != 4);
        
    }

    private void creatBoard() {
        System.out.println("Enter the name of the board:");
        String name = scan.nextLine();
        var board = new BoardEntity(name);

        System.out.println("Need more than the standard 3 columns in your Board? If you do, enter the number, if you don't, enter 0 (zero): ");
        int columnsNumber = scan.nextInt();

        List<BoardColumnEntity> boardColumns = new ArrayList();

        System.out.println("Enter the name of initial column: ");
        String firstColumnName = scan.nextLine();
        var fistColumn = createColumn(firstColumnName, BoardColumnTypeEnum.INITIAL, 0);
        boardColumns.add(fistColumn);

        for(int i = 1; i < (3 + columnsNumber); i++) {
            System.out.println("Enter the name of the next column: ");
            var pendingColumnName = scan.nextLine();
            var pendingColumn = createColumn(pendingColumnName, BoardColumnTypeEnum.PENDING, i);
            boardColumns.add(pendingColumn);
        }

        try(var connection = connect()) {
            var service = new BoardService(connection);
            board = service.insert(board);
            System.out.printf("Board %s, id %d, was created.\n", board.getName(), board.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void viewBoard() {
        System.out.println("Enter the id of the board:");
        var id = scan.nextLong();
        System.out.println("Viewing board with id: " + id);
    }

    private void deleteBoard() {
        System.out.println("Enter the id of the board:");
        var id = scan.nextLong();
        try(var connection = connect()) {
            var service = new BoardService(connection);
            if (service.delete(id)) {
                System.out.printf("Board %d was deleted.\n", id);
            } else {
                System.out.printf("Board %d not found.\n", id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void exit() {
        System.out.println("Exiting...");
    }

    private BoardColumnEntity createColumn(String name, BoardColumnTypeEnum type, int column_order) {
        var boardColumn = new BoardColumnEntity();
        boardColumn.setName(name);
        boardColumn.setType(type);
        boardColumn.setColumn_order(column_order);
        return boardColumn;
    }

}
                    
                   

