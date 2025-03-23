package com.cardboard;

import com.cardboard.persistence.config.ConnectionConfig;
import com.cardboard.persistence.migration.MigrationStrategy;

public class Main {
    public static void main(String[] args) {

        try (var connection = ConnectionConfig.connect()) {
            new MigrationStrategy(connection).executeMigration();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}