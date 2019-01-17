package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.dao.AuthorDao;
import ru.otus.model.Author;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
class AuthorServiceImplTest {

    @Autowired
    private AuthorServiceImpl authorService;

    @MockBean
    private AuthorDao dao;

    private Author author;

    @BeforeEach
    void init() {
        author = new Author(1, "Test", "Testov");
    }

    @Test
    void create() {
        authorService.create(author);
        verify(dao, times(1)).create(author);
    }

    @Test
    void update() {
        when(dao.getById(1)).thenReturn(author);
        authorService.update(new Author(1, "name", "lastname"));
        verify(dao, times(1)).update(author);
        verify(dao, times(1)).getById(author.getId());
        assertThat(author)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "name")
                .hasFieldOrPropertyWithValue("lastName", "lastname");
    }

    @Test
    void getById() {
        when(dao.getById(1)).thenReturn(author);
        Author actual = authorService.getById(1);
        verify(dao, times(1)).getById(1);
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", author.getId())
                .hasFieldOrPropertyWithValue("name", author.getName())
                .hasFieldOrPropertyWithValue("lastName", author.getLastName());
    }

    @Test
    void getAll() {
        when(dao.getAll()).thenReturn(Collections.singletonList(author));
        List<Author> list = authorService.getAll();
        verify(dao, times(1)).getAll();
        assertEquals(1, list.size());
        assertThat(list.get(0))
                .hasFieldOrPropertyWithValue("id", author.getId())
                .hasFieldOrPropertyWithValue("name", author.getName())
                .hasFieldOrPropertyWithValue("lastName", author.getLastName());
    }

    @Test
    void delete() {
        authorService.delete(1);
        verify(dao, times(1)).delete(1);
    }

}