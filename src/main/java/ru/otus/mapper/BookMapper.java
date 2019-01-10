package ru.otus.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Nullable
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Book(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("summary"),
                new Author(
                        rs.getInt("author_id"),
                        rs.getString("author_name"),
                        rs.getString("lastname")
                ),
                new Genre(
                        rs.getInt("genre_id"),
                        rs.getString("genre_name")
                )
        );
    }
}
