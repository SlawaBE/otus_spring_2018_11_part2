package ru.otus.service;

import ru.otus.entity.Book;

import java.util.List;

public interface BookService {

    Book update(Book book);

    Book getById(String id);

    List<Book> getAll();

    void delete(String id);

    List<Book> findByAuthor(String name);

    List<Book> findByGenre(String genre);

}
