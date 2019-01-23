package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.entity.Genre;
import ru.otus.repository.GenreRepository;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith({SpringExtension.class, MockitoExtension.class})
@Import(GenreServiceImpl.class)
class GenreServiceImplTest {

    @Autowired
    private GenreServiceImpl genreService;

    @MockBean
    private GenreRepository repository;

    private Genre genre;

    @BeforeEach
    void init() {
        genre = new Genre(1, "Test");
    }

    @Test
    void create() {
        genreService.create(genre);
        verify(repository, times(1)).create(genre);
    }

    @Test
    void update() {
        genreService.update(genre);
        verify(repository, times(1)).update(genre);
    }

    @Test
    void getById() {
        when(repository.getById(1)).thenReturn(genre);
        Genre actual = genreService.getById(1);
        verify(repository, times(1)).getById(1);
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", genre.getId())
                .hasFieldOrPropertyWithValue("name", genre.getName());
    }

    @Test
    void getAll() {
        when(repository.getAll()).thenReturn(Collections.singletonList(genre));
        List<Genre> list = genreService.getAll();
        verify(repository, times(1)).getAll();
        assertThat(list.get(0))
                .hasFieldOrPropertyWithValue("id", genre.getId())
                .hasFieldOrPropertyWithValue("name", genre.getName());
    }

    @Test
    void delete() {
        genreService.delete(1);
        verify(repository, times(1)).delete(1);
    }

}