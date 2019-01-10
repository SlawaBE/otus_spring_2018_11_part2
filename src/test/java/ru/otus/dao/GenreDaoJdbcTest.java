package ru.otus.dao;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import ru.otus.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@JdbcTest(properties = "spring.datasource.data=testdata.sql")
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    @Autowired
    private GenreDaoJdbc jdbc;

    @Test
    void createTest() {
        Genre expected = new Genre("Test");
        int id = jdbc.create(expected);
        Genre actual = jdbc.getById(id);
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("name", "Test");
    }

    @Test
    void updateTest() {
        Genre expected = new Genre(1, "Test");
        jdbc.update(expected);
        Genre actual = jdbc.getById(1);
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "Test");
    }

    @Test
    void getAllTest() {
        List<Genre> authors = jdbc.getAll();
        assertEquals(authors.size(), 3);
    }

    @Test
    void getByIdTest() {
        Genre author = jdbc.getById(1);
        assertThat(author)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "Genre1");
    }

    @Test
    void deleteTest() {
        assertNotNull(jdbc.getById(3));
        assertTrue(jdbc.delete(3));
        assertNull(jdbc.getById(3));
        assertFalse(jdbc.delete(1));
    }
}