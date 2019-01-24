package ru.otus.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.entity.Author;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest(properties = "spring.datasource.data=testdata.sql")
@Transactional
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository repositoryJpa;

    @Test
    void createTest() {
        Author expected = new Author("Test", "Testov");
        int id = repositoryJpa.save(expected).getId();
        Author actual = repositoryJpa.findById(id).get();
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("name", "Test")
                .hasFieldOrPropertyWithValue("lastName", "Testov");
    }

    @Test
    void updateTest() {
        Author expected = new Author(1, "Test", "Testov");
        repositoryJpa.save(expected);
        Author actual = repositoryJpa.findById(1).get();
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "Test")
                .hasFieldOrPropertyWithValue("lastName", "Testov");
    }

    @Test
    void getAllTest() {
        List<Author> authors = new ArrayList<>();
        repositoryJpa.findAll().forEach(authors::add);
        assertEquals(authors.size(), 3);
    }

    @Test
    void getByIdTest() {
        Author author = repositoryJpa.findById(1).get();
        assertThat(author)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "Name1")
                .hasFieldOrPropertyWithValue("lastName", "LastName1");
    }

    @Test
    void deleteTest() {
        assertTrue(repositoryJpa.findById(3).isPresent());
        repositoryJpa.deleteById(3);
        assertFalse(repositoryJpa.findById(3).isPresent());
    }
}