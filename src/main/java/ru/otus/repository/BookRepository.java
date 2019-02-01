package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.entity.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {

    @Query("{ 'authors' : { $regex: ?0 } }")
    List<Book> findByAuthor(String author);

    List<Book> findByGenresContaining(String genre);

}
