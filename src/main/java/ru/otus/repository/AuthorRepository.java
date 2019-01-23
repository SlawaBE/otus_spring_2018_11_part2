package ru.otus.repository;

import ru.otus.entity.Author;

import java.util.List;

public interface AuthorRepository {

    int create(Author author);

    void update(Author author);

    Author getById(int id);

    List<Author> getAll();

    void delete(int id);
}
