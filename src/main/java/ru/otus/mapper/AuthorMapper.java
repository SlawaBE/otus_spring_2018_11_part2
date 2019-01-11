package ru.otus.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import ru.otus.model.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements RowMapper<Author> {

    @Nullable
    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Author(
                rs.getInt("author_id"),
                rs.getString("author_name"),
                rs.getString("author_lastname")
        );
    }
}