package ru.otus.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.batch.domain.GenreSql;

public interface GenreJpaRepository extends JpaRepository<GenreSql, Long> {

    GenreSql findByName(String name);

}
