package ru.otus.dao;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.model.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@JdbcTest(properties = "spring.datasource.data=testdata.sql")
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDaoJdbc jdbc;

    @Test
    void createTest() {
        Author expected = new Author("Test", "Testov");
        int id = jdbc.create(expected);
        Author actual = jdbc.getById(id);
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("name", "Test")
                .hasFieldOrPropertyWithValue("lastName", "Testov");
    }

    @Test
    void updateTest() {
        Author expected = new Author(1, "Test", "Testov");
        jdbc.update(expected);
        Author actual = jdbc.getById(1);
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "Test")
                .hasFieldOrPropertyWithValue("lastName", "Testov");
    }

    @Test
    void getAllTest() {
        List<Author> authors = jdbc.getAll();
        assertEquals(authors.size(), 3);
    }

    @Test
    void getByIdTest() {
        Author author = jdbc.getById(1);
        assertThat(author)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "Name1")
                .hasFieldOrPropertyWithValue("lastName", "LastName1");
    }

    @Test
    void deleteTest() {
        assertNotNull(jdbc.getById(3));
        assertTrue(jdbc.delete(3));
        assertNull(jdbc.getById(3));
        assertFalse(jdbc.delete(1));
    }
}