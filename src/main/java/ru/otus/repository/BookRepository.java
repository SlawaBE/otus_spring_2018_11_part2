package ru.otus.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.entity.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {

}
