package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.entity.Book;
import ru.otus.repository.BookRepository;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public int create(Book book) {
        return repository.create(book);
    }

    @Override
    public void update(Book book) {
        repository.update(book);
    }

    @Override
    public Book getById(int id) {
        return repository.getById(id);
    }

    @Override
    public List<Book> getAll() {
        return repository.getAll();
    }

    @Override
    public void delete(int id) {
        repository.delete(id);
    }

}
