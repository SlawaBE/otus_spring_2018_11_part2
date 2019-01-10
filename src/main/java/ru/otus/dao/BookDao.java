package ru.otus.dao;

import ru.otus.model.Book;

import java.util.List;

public interface BookDao {

    int create(Book book);

    void update(Book book);

    Book getById(int id);

    List<Book> getAll();

    void delete(int id);

}
