package com.codecool.bookdb.model;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoJdbc implements BookDao {

    private DataSource dataSource;
    private AuthorDao authorDao;

    public BookDaoJdbc(DataSource dataSource, AuthorDao authorDao) {
        this.dataSource = dataSource;
        this.authorDao = authorDao;
    }

    @Override
    public void add(Book book) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO book (author_id, title) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, book.getAuthor().getId());
            statement.setString(2, book.getTitle());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            book.setId(generatedKeys.getInt(1));

        } catch (SQLException e) {
            throw new RuntimeException("Error, cannot add new Book", e);
        }
    }

    @Override
    public void update(Book book) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE book SET author_id = ?, title = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, book.getAuthor().getId());
            statement.setString(2, book.getTitle());
            statement.setInt(3, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error, cannot update book with id=" + book.getId(), e);
        }
    }

    @Override
    public Book get(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT author_id, title FROM book WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            String title = resultSet.getString(2);
            int authorId = resultSet.getInt(1);
            Author author = authorDao.get(authorId);
            Book book = new Book(author, title);
            book.setId(id);
            return book;
        } catch (SQLException e) {
            throw new RuntimeException("Error, cannot read Book with id=" + id, e);
        }
    }

    @Override
    public List<Book> getAll() {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT id, author_id, title FROM book";
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int authorId = resultSet.getInt(2);
                String title = resultSet.getString(3);

                Author author = authorDao.get(authorId);

                Book book = new Book(author, title);
                book.setId(id);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException("Error, cannot read all books", e);
        }
    }
}
