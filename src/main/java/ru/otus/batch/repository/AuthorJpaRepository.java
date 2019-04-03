package ru.otus.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.batch.domain.AuthorSql;

public interface AuthorJpaRepository extends JpaRepository<AuthorSql, Long> {

    AuthorSql findByName(String name);

}
