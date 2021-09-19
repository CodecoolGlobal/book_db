package com.codecool.bookdb.model;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDaoJdbc implements AuthorDao {

    private DataSource dataSource;

    public AuthorDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Author author) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO author (first_name, last_name, birth_date) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, author.getFirstName());
            statement.setString(2, author.getLastName());
            statement.setDate(3, author.getBirthDate());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            author.setId(generatedKeys.getInt(1));

        } catch (SQLException e) {
            throw new RuntimeException("Error, cannot add new Author", e);
        }
    }

    @Override
    public void update(Author author) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE author SET first_name = ?, last_name = ?, birth_date = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, author.getFirstName());
            statement.setString(2, author.getLastName());
            statement.setDate(3, author.getBirthDate());
            statement.setInt(4, author.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error, cannot update Author with id=" + author.getId(), e);
        }
    }

    @Override
    public Author get(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT first_name, last_name, birth_date FROM author WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            Author author = new Author(resultSet.getString(1), resultSet.getString(2), resultSet.getDate(3));
            author.setId(id);
            return author;
        } catch (SQLException e) {
            throw new RuntimeException("Error, cannot read Author with id=" + id, e);
        }
    }

    @Override
    public List<Author> getAll() {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT first_name, last_name, birth_date, id FROM author";
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            List<Author> authors = new ArrayList<>();
            while (resultSet.next()) {
                Author author = new Author(
                        resultSet.getInt(4),
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDate(3));
                authors.add(author);
            }
            return authors;
        } catch (SQLException e) {
            throw new RuntimeException("Error, cannot readall authors", e);
        }
    }
}
