package ru.otus.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.entity.Author;

public interface AuthorRepository extends CrudRepository<Author, Integer> {

}
