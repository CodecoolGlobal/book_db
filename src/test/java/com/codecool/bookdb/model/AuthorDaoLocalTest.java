package com.codecool.bookdb.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;


import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthorDaoLocalTest {


  @Test
  public void whenAddingNewAuthorViaDao_addsAuthorCorrectlyAndSetItsID() {
    // given
    AuthorDao authorDaoLocal = new AuthorDaoLocal();
    Author author = new Author("Jan", "Kowalski", Date.valueOf("2000-01-01"));
    // when
    authorDaoLocal.add(author);
    // then
    List<Author> all = authorDaoLocal.getAll();
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