package ru.otus.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import ru.otus.entity.Genre;
import ru.otus.repository.GenreRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
@Import(GenreServiceImpl.class)
class GenreServiceImplTest {

    @InjectMocks
    private GenreServiceImpl genreService;

    @Mock
    private GenreRepository repository;

    @Test
    void create() {
        Genre genre = genre();
        when(repository.save(genre)).thenReturn(genre);
        genreService.create(genre);
        verify(repository, times(1)).save(genre);
    }

    @Test
    void update() {
        Genre genre = genre();
        genreService.update(genre);
        verify(repository, times(1)).save(genre);
    }

    @Test
    void getById() {
        Genre genre = genre();
        when(repository.findById(1)).thenReturn(Optional.of(genre));
        Genre actual = genreService.getById(1);
        verify(repository, times(1)).findById(1);
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", genre.getId())
                .hasFieldOrPropertyWithValue("name", genre.getName());
    }

    @Test
    void getAll() {
        Genre genre = genre();
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

    private Genre genre() {
        return new Genre(1, "Test");
    }

}