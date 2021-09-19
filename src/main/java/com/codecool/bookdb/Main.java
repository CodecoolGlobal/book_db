package com.codecool.bookdb;

import com.codecool.bookdb.model.AuthorDao;
import com.codecool.bookdb.model.AuthorDaoJdbc;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        BookDatabaseManager manager = new BookDatabaseManager();
        AuthorDao dao = new AuthorDaoJdbc(manager.connect());

//        Author janKowalski = new Author("Jan", "Kowalski", Date.valueOf("1990-06-10"));
//        dao.add(janKowalski);
//        dao.add(new Author("Paweł", "Kowalski", Date.valueOf("1995-05-10")));
//        Integer kowalksiId = janKowalski.getId();

        System.out.println(dao.getAll());


    }
}
