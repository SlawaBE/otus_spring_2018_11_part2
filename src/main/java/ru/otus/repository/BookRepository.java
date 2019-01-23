package ru.otus.repository;

import ru.otus.entity.Book;

import java.util.List;

public interface BookRepository {

    int create(Book book);

    void update(Book book);

    Book getById(int id);

    List<Book> getAll();

    void delete(int id);

}
