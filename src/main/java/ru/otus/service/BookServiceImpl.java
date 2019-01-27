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
        return repository.save(book).getId();
    }

    @Override
    public void update(Book book) {
        repository.save(book);
    }

    @Override
    public Book getById(int id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Book> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

}
