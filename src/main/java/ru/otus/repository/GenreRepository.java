package ru.otus.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.entity.Genre;

public interface GenreRepository extends CrudRepository<Genre, Integer> {

}
