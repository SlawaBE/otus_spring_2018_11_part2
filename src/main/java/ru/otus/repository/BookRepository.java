package ru.otus.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.entity.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    @Query("{ 'authors' : { $regex: ?0 } }")
    Flux<Book> findByAuthor(String author);

    Flux<Book> findByGenresContaining(String genre);

}
