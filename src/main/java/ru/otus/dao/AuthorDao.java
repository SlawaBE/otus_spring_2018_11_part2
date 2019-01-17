package ru.otus.dao;

import ru.otus.model.Author;

import java.util.List;

public interface AuthorDao {

    int create(Author author);

    void update(Author author);

    Author getById(int id);

    List<Author> getAll();

    boolean delete(int id);
}
