package ru.otus.service;

import ru.otus.model.Author;

import java.util.List;

public interface AuthorService {

    int create(Author genre);

    void update(Author genre);

    Author getById(int id);

    List<Author> getAll();

    boolean delete(int id);
}
