package ru.otus.dao;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.mapper.AuthorMapper;
import ru.otus.model.Author;

import java.util.Collections;
import java.util.List;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int create(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("name", author.getName());
        source.addValue("lastname", author.getLastName());
        jdbc.update("insert into authors (name, lastname) values (:name, :lastname);", source, keyHolder, new String[]{"id"});
        return keyHolder.getKey().intValue();
    }

    @Override
    public void update(Author author) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("id", author.getId());
        source.addValue("name", author.getName());
        source.addValue("lastname", author.getLastName());
        jdbc.update("update authors set name = :name, lastname = :lastname where id = :id;", source);
    }

    @Override
    public Author getById(int id) {
        try {
            return jdbc.queryForObject(
                    "select * from authors where id = :id",
                    Collections.singletonMap("id", id),
                    new AuthorMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select * from authors", new AuthorMapper());
    }

    @Override
    public boolean delete(int id) {
        try {
            jdbc.update("delete from authors where id = :id", Collections.singletonMap("id", id));
            return true;
        } catch (DataIntegrityViolationException e) {
            return false;
        }
    }
}
