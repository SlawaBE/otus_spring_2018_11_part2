package ru.otus.service;

import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.entity.Book;

public interface BookService {

    Mono<Book> update(Book book, UserDetails userDetails);

    Mono<Book> getById(String id);

    Flux<Book> getAll();

    Mono<Void> delete(String id);

    Flux<Book> findByAuthor(String name);

    Flux<Book> findByGenre(String genre);

}
