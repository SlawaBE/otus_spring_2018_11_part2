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
import ru.otus.entity.Book;
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
class BookRepositoryJpaTest {

    @Autowired
    private BookRepositoryJpa repositoryJpa;

    @Test
    void createTest() {
        Book expected = new Book("Test", "description", new Author(1, null, "lastname"), new Genre(1, null));
        int id = repositoryJpa.create(expected);
        Book actual = repositoryJpa.getById(id);
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
        repositoryJpa.update(expected);
        Book actual = repositoryJpa.getById(1);
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "Test")
                .hasFieldOrPropertyWithValue("summary", "description")
                .hasFieldOrPropertyWithValue("author.id", 1)
                .hasFieldOrPropertyWithValue("genre.id", 1);
    }

    @Test
    void getAllTest() {
        List<Book> authors = repositoryJpa.getAll();
        assertEquals(authors.size(), 2);
    }

    @Test
    void getByIdTest() {
        Book author = repositoryJpa.getById(1);
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
        assertNotNull(repositoryJpa.getById(1));
        repositoryJpa.delete(1);
        assertThrows(EmptyResultDataAccessException.class, () -> repositoryJpa.getById(1));
    }

}