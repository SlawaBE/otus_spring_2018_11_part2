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
import java.util.Optional;

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
        when(repository.save(genre)).thenReturn(genre);
        genreService.create(genre);
        verify(repository, times(1)).save(genre);
    }

    @Test
    void update() {
        genreService.update(genre);
        verify(repository, times(1)).save(genre);
    }

    @Test
    void getById() {
        when(repository.findById(1)).thenReturn(Optional.of(genre));
        Genre actual = genreService.getById(1);
        verify(repository, times(1)).findById(1);
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", genre.getId())
                .hasFieldOrPropertyWithValue("name", genre.getName());
    }

    @Test
    void getAll() {
        when(repository.findAll()).thenReturn(Collections.singletonList(genre));
        List<Genre> list = genreService.getAll();
        verify(repository, times(1)).findAll();
        assertThat(list.get(0))
                .hasFieldOrPropertyWithValue("id", genre.getId())
                .hasFieldOrPropertyWithValue("name", genre.getName());
    }

    @Test
    void delete() {
        genreService.delete(1);
        verify(repository, times(1)).deleteById(1);
    }

}