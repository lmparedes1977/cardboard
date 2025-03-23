package com.cardboard;

import com.cardboard.persistence.config.SQLiteConnection;
import com.cardboard.persistence.migration.MigrationStrategy;

import lombok.experimental.var;

public class Main {
    public static void main(String[] args) {

        try (var connection = SQLiteConnection.connect()) {
            new MigrationStrategy(connection).executeMigration();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}