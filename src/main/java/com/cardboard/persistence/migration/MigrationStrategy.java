package com.cardboard.persistence.migration;

import java.io.FileOutputStream;
import java.sql.Connection;

import com.cardboard.persistence.config.SQLiteConnection;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MigrationStrategy {

    private final Connection connection;

    public void executeMigration() {
        var originalOutput = System.out;
        var originalError = System.err;        

        try ( var fos = new FileOutputStream("liquibase.log")){
            System.setOut(new java.io.PrintStream(fos));
            System.setErr(new java.io.PrintStream(fos));
            System.setErr(originalError);
            try(var connection = SQLiteConnection.connect()){
                var jdbcConnection = new JdbcConnection(connection);
                var liquibase = new Liquibase(
                    "/db/changelog/db.changelog-master.yml",
                    new ClassLoaderResourceAccessor(),
                    jdbcConnection);
                    liquibase.update();
                    liquibase.close();
            } catch (Exception e) {
            }
        } catch (Exception e) {
        } finally {
            System.setOut(originalOutput);
            System.setErr(originalError);
        }
        
    
        
    }
}

