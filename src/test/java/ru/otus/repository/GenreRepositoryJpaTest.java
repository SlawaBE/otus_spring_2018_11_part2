package ru.otus.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.entity.Genre;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
        "spring.datasource.data=testdata.sql"
})
@Transactional
class GenreRepositoryJpaTest {

    @Autowired
    private GenreRepositoryJpa repositoryJpa;

    @Test
    void createTest() {
        Genre expected = new Genre("Test");
        int id = repositoryJpa.create(expected);
        Genre actual = repositoryJpa.getById(id);
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("name", "Test");
    }

    @Test
    void updateTest() {
        Genre expected = new Genre(1, "Test");
        repositoryJpa.update(expected);
        Genre actual = repositoryJpa.getById(1);
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "Test");
    }

    @Test
    void getAllTest() {
        List<Genre> authors = repositoryJpa.getAll();
        assertEquals(authors.size(), 3);
    }

    @Test
    void getByIdTest() {
        Genre author = repositoryJpa.getById(1);
        assertThat(author)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "Genre1");
    }

    @Test
    void deleteTest() {
        assertNotNull(repositoryJpa.getById(3));
        repositoryJpa.delete(3);
        assertThrows(EmptyResultDataAccessException.class, () -> repositoryJpa.getById(3));
    }

}