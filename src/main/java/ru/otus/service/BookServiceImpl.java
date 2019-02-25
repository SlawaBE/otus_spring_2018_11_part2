package ru.otus.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.entity.Book;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    private final CommentRepository commentRepository;

    public BookServiceImpl(BookRepository repository, CommentRepository commentRepository) {
        this.repository = repository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Mono<Book> update(Book book) {
        return repository.save(book);
    }

    @Override
    public Mono<Book> getById(String id) {
        return repository.findById(id);
    }

    @Override
    public Flux<Book> getAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Void> delete(String id) {
        return repository.deleteById(id).and(commentRepository.deleteByBookId(id));
    }

    @Override
    public Flux<Book> findByAuthor(String name) {
        return repository.findByAuthor(name);
    }

    @Override
    public Flux<Book> findByGenre(String genre) {
        return repository.findByGenresContaining(genre);
    }

}
