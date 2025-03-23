package com.cardboard.persistence.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLiteConnection {

    public static Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:/home/leonardo-paredes/estudos/JAVA/cardboard/teste.db";
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

}
