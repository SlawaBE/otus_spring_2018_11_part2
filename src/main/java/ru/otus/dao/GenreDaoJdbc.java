package ru.otus.dao;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.mapper.GenreMapper;
import ru.otus.model.Genre;

import java.util.Collections;
import java.util.List;

@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int create(Genre genre) {
        MapSqlParameterSource source = new MapSqlParameterSource(Collections.singletonMap("name", genre.getName()));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("insert into genres (genre_name) values (:name);", source, keyHolder, new String[]{"genre_id"});
        return keyHolder.getKey().intValue();
    }

    public void update(Genre genre) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("id", genre.getId());
        source.addValue("name", genre.getName());
        jdbc.update("update genres set genre_name = :name where genre_id = :id", source);
    }

    @Override
    public Genre getById(int id) {
        try {
            return jdbc.queryForObject(
                    "select * from genres where genre_id = :id",
                    Collections.singletonMap("id", id),
                    new GenreMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select * from genres;", new GenreMapper());
    }

    @Override
    public boolean delete(int id) {
        try {
            jdbc.update("delete from genres where genre_id = :id", Collections.singletonMap("id", id));
            return true;
        } catch (DataIntegrityViolationException e) {
            return false;
        }
    }
}
