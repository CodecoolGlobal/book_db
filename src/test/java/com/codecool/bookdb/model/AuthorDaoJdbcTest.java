package com.codecool.bookdb.model;

import com.codecool.bookdb.BookDatabaseManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthorDaoJdbcTest {
  private BookDatabaseManager manager;
  private AuthorDao authorDao;

  @BeforeEach
  public void clearDb() throws SQLException {
    manager = new BookDatabaseManager();
    authorDao = new AuthorDaoJdbc(manager.connect());
    try (Connection connection = manager.connect().getConnection()) {
      String sql = "TRUNCATE author CASCADE;";
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Error, cannot connect database", e);
    }
  }

  @Test
  public void whenAddingNewAuthorViaDao_addsAuthorCorrectlyAndSetItsID() throws SQLException {
    // given
    Author author = new Author("Jan", "Kowalski", Date.valueOf("2000-01-01"));
    // when
    authorDao.add(author);
    // then
    List<Author> all = authorDao.getAll();
    assertNotNull(all);
    assertEquals(1, all.size());
    Author testedAuthor = all.get(0);
    assertEquals("Jan", testedAuthor.getFirstName());
    assertEquals("Kowalski", testedAuthor.getLastName());
    assertEquals(Date.valueOf("2000-01-01"), testedAuthor.getBirthDate());
    assertNotNull(testedAuthor.getId());
  }

  @Test
  public void whenAuthorIsNull_ThrowsIllegalArgumentExceptionWithCorrectMessage() {
    // given
    AuthorDao authorDaoLocal = new AuthorDaoLocal();
    Author author = null;
    // when
    Executable e = () -> authorDaoLocal.add(author);
    // then
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, e);
    assertEquals("author doesnt exist", exception.getMessage());
  }

}