package com.codecool.bookdb.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AuthorDaoLocal implements AuthorDao {

    private List<Author> storage = new ArrayList<>();

    @Override
    public void add(Author author) {
        if (author == null)
            throw new IllegalArgumentException("author doesnt exist");
        author.setId(new Random().nextInt());
        storage.add(author);
    }

    @Override
    public void update(Author author) {
        for (Author storageAuthor : storage) {
            if (storageAuthor.getId().equals(author.getId())) {
                storageAuthor.setFirstName(author.getFirstName());
                storageAuthor.setLastName(author.getLastName());
                storageAuthor.setBirthDate(author.getBirthDate());
                return;
            }
        }
        throw new IllegalArgumentException("Update failed, no author in storage");
    }

    @Override
    public Author get(int id) {
        for (Author storageAuthor : storage) {
            if (storageAuthor.getId().equals(id)) {
                return storageAuthor;
            }
        }
        throw new IllegalArgumentException("No author with id=" + id);
    }

    @Override
    public List<Author> getAll() {
        return storage;
    }
}
