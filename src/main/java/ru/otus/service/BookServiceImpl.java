package ru.otus.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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
    @HystrixCommand(groupKey = "bookService", commandKey = "saveBook")
    public Book update(Book book) {
        return repository.save(book);
    }

    @Override
    @HystrixCommand(groupKey = "bookService", commandKey = "getBook")
    public Book getById(String id) {
        return repository.findById(id).get();
    }

    @Override
    @HystrixCommand(groupKey = "bookService", commandKey = "getAllBook")
    public List<Book> getAll() {
        return repository.findAll();
    }

    @Override
    @HystrixCommand(groupKey = "bookService", commandKey = "deleteBook")
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<Book> findByAuthor(String name) {
        return repository.findByAuthor(name);
    }

    @Override
    public List<Book> findByGenre(String genre) {
        return repository.findByGenresContaining(genre);
    }

}
