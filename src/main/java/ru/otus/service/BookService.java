package ru.otus.service;

import ru.otus.model.Book;

import java.util.List;

public interface BookService {

    int create(Book book);

    void update(Book book);

    Book getById(int id);

    List<Book> getAll();

    void delete(int id);
}
