package com.cardboard;

import com.cardboard.persistence.config.ConnectionConfig;
import com.cardboard.persistence.migration.MigrationStrategy;
import com.cardboard.ui.MainMenu;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        try (var connection = ConnectionConfig.connect()) {
            new MigrationStrategy(connection).executeMigration();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        new MainMenu().execute();
    }
}