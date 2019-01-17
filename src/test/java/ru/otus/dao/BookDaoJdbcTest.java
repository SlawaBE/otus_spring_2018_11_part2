package ru.otus.dao;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@JdbcTest(properties = "spring.datasource.data=testdata.sql")
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    @Autowired
    private BookDaoJdbc jdbc;

    @Test
    void createTest() {
        Book expected = new Book("Test", "description", new Author(1, null, "lastname"), new Genre(1, null));
        int id = jdbc.create(expected);
        Book actual = jdbc.getById(id);
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("name", "Test")
                .hasFieldOrPropertyWithValue("summary", "description")
                .hasFieldOrPropertyWithValue("author.id", 1)
                .hasFieldOrPropertyWithValue("genre.id", 1);
    }

    @Test
    void updateTest() {
        Book expected = new Book(1, "Test", "description", new Author(1, "name", "lastname"), new Genre(1, "name"));
        jdbc.update(expected);
        Book actual = jdbc.getById(1);
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "Test")
                .hasFieldOrPropertyWithValue("summary", "description")
                .hasFieldOrPropertyWithValue("author.id", 1)
                .hasFieldOrPropertyWithValue("genre.id", 1);
    }

    @Test
    void getAllTest() {
        List<Book> authors = jdbc.getAll();
        assertEquals(authors.size(), 2);
    }

    @Test
    void getByIdTest() {
        Book author = jdbc.getById(1);
        assertThat(author)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "Book_Name1")
                .hasFieldOrPropertyWithValue("summary", "Book_Summary1")
                .hasFieldOrPropertyWithValue("author.id", 1)
                .hasFieldOrPropertyWithValue("author.name", "Name1")
                .hasFieldOrPropertyWithValue("author.lastName", "LastName1")
                .hasFieldOrPropertyWithValue("genre.id", 1)
                .hasFieldOrPropertyWithValue("genre.name", "Genre1");
    }

    @Test
    void deleteTest() {
        assertNotNull(jdbc.getById(1));
        jdbc.delete(1);
        assertNull(jdbc.getById(1));
    }
}