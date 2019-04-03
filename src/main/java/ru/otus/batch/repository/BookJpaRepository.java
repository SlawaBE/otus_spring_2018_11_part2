package ru.otus.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.batch.domain.BookSql;

public interface BookJpaRepository extends JpaRepository<BookSql, Long> {
}
