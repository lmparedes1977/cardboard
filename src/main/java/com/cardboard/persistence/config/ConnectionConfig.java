package com.cardboard.persistence.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionConfig {

    public static Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:postgresql://localhost:5432/cardboard?user=root&password=root";
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

}
