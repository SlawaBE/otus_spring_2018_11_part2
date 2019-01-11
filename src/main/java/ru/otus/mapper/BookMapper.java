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
                rs.getInt("book_id"),
                rs.getString("book_name"),
                rs.getString("book_summary"),
                new AuthorMapper().mapRow(rs, rowNum),
                new GenreMapper().mapRow(rs, rowNum)
        );
    }
}
