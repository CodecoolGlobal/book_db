package com.codecool.bookdb;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class BookDatabaseManager {

    public DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setDatabaseName("books");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");

        System.out.println("Trying to connect...");
        dataSource.getConnection().close();
        System.out.println("Connected successfully");

        return dataSource;
    }
}
