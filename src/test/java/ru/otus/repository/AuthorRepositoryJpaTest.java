package ru.otus.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.entity.Author;

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
class AuthorRepositoryJpaTest {

    @Autowired
    private AuthorRepositoryJpa repositoryJpa;

    @Test
    void createTest() {
        Author expected = new Author("Test", "Testov");
        int id = repositoryJpa.create(expected);
        Author actual = repositoryJpa.getById(id);
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("name", "Test")
                .hasFieldOrPropertyWithValue("lastName", "Testov");
    }

    @Test
    void updateTest() {
        Author expected = new Author(1, "Test", "Testov");
        repositoryJpa.update(expected);
        Author actual = repositoryJpa.getById(1);
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "Test")
                .hasFieldOrPropertyWithValue("lastName", "Testov");
    }

    @Test
    void getAllTest() {
        List<Author> authors = repositoryJpa.getAll();
        assertEquals(authors.size(), 3);
    }

    @Test
    void getByIdTest() {
        Author author = repositoryJpa.getById(1);
        assertThat(author)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "Name1")
                .hasFieldOrPropertyWithValue("lastName", "LastName1");
    }

    @Test
    void deleteTest() {
        assertNotNull(repositoryJpa.getById(3));
        repositoryJpa.delete(3);
        assertThrows(EmptyResultDataAccessException.class, () -> repositoryJpa.getById(3));
    }
}