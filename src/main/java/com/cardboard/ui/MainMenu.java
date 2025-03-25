package com.cardboard.ui;

import java.util.Scanner;

import lombok.AllArgsConstructor;

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
        var name = scan.next();
        System
        .out.println("Board created with name: " + name);
    }

    private void viewBoard() {
        System.out.println("Enter the id of the board:");
        var id = scan.nextLong();
        System.out.println("Viewing board with id: " + id);
    }

    private void deleteBoard() {
        System.out.println("Enter the id of the board:");
        var id = scan.nextLong();
        System.out.println("Deleting board with id: " + id);
    }

    private void exit() {
        System.out.println("Exiting...");
    }

}
                    
                   

