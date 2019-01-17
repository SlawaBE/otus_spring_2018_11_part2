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
import ru.otus.dao.GenreDao;
import ru.otus.model.Genre;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
class GenreServiceImplTest {

    @Autowired
    private GenreServiceImpl genreService;

    @MockBean
    private GenreDao dao;

    private Genre genre;

    @BeforeEach
    void init() {
        genre = new Genre(1, "Test");
    }

    @Test
    void create() {
        genreService.create(genre);
        verify(dao, times(1)).create(genre);
    }

    @Test
    void update() {
        genreService.update(genre);
        verify(dao, times(1)).update(genre);
    }

    @Test
    void getById() {
        when(dao.getById(1)).thenReturn(genre);
        Genre actual = genreService.getById(1);
        verify(dao, times(1)).getById(1);
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", genre.getId())
                .hasFieldOrPropertyWithValue("name", genre.getName());
    }

    @Test
    void getAll() {
        when(dao.getAll()).thenReturn(Collections.singletonList(genre));
        List<Genre> list = genreService.getAll();
        verify(dao, times(1)).getAll();
        assertThat(list.get(0))
                .hasFieldOrPropertyWithValue("id", genre.getId())
                .hasFieldOrPropertyWithValue("name", genre.getName());
    }

    @Test
    void delete() {
        genreService.delete(1);
        verify(dao, times(1)).delete(1);
    }

}