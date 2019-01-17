package ru.otus.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.mapper.BookMapper;
import ru.otus.model.Book;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int create(Book book) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", book.getName());
        params.put("summary", book.getSummary());
        params.put("author_id", book.getAuthor().getId());
        params.put("genre_id", book.getGenre().getId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(
                "insert into books (book_name, book_summary, author_id, genre_id) values (:name, :summary, :author_id, :genre_id);",
                new MapSqlParameterSource(params),
                keyHolder,
                new String[]{"book_id"});
        return keyHolder.getKey().intValue();
    }

    @Override
    public void update(Book book) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("id", book.getId());
        source.addValue("name", book.getName());
        source.addValue("summary", book.getSummary());
        source.addValue("author_id", book.getAuthor().getId());
        source.addValue("genre_id", book.getGenre().getId());
        jdbc.update("update books set book_name = :name, book_summary = :summary, author_id = :author_id, genre_id = :genre_id where book_id = :id;", source);
    }

    @Override
    public Book getById(int id) {
        try {
            return jdbc.queryForObject(
                    "select book_id id, book_name name, book_summary, books.author_id, author_name, author_lastname, books.genre_id, genre_name\n" +
                            "from books\n" +
                            "left join authors on books.author_id = authors.author_id\n" +
                            "left join genres on books.genre_id = genres.genre_id\n" +
                            "where book_id = :id",
                    new MapSqlParameterSource(Collections.singletonMap("id", id)),
                    new BookMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select book_id id, book_name name, book_summary, books.author_id, author_name, author_lastname, books.genre_id, genre_name\n" +
                "from books\n" +
                "left join authors on books.author_id = authors.author_id\n" +
                "left join genres on books.genre_id = genres.genre_id\n", new BookMapper());
    }

    @Override
    public void delete(int id) {
        jdbc.update("delete from books where book_id = :id", Collections.singletonMap("id", id));
    }
}
