package ru.otus.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import ru.otus.entity.Author;
import ru.otus.repository.AuthorRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
@Import(AuthorServiceImpl.class)
class AuthorServiceImplTest {

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Mock
    private AuthorRepository repository;

    @Test
    void create() {
        Author author = author();
        when(repository.save(author)).thenReturn(author);
        authorService.create(author);
        verify(repository, times(1)).save(author);
    }

    @Test
    void update() {
        Author author = author();
        authorService.update(author);
        verify(repository, times(1)).save(author);
    }

    @Test
    void getById() {
        Author author = author();
        when(repository.findById(1)).thenReturn(Optional.of(author));
        Author actual = authorService.getById(1);
        verify(repository, times(1)).findById(1);
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", author.getId())
                .hasFieldOrPropertyWithValue("name", author.getName())
                .hasFieldOrPropertyWithValue("lastName", author.getLastName());
    }

    @Test
    void getAll() {
        Author author = author();
        when(repository.findAll()).thenReturn(Collections.singletonList(author));
        List<Author> list = authorService.getAll();
        verify(repository, times(1)).findAll();
        assertEquals(1, list.size());
        assertThat(list.get(0))
                .hasFieldOrPropertyWithValue("id", author.getId())
                .hasFieldOrPropertyWithValue("name", author.getName())
                .hasFieldOrPropertyWithValue("lastName", author.getLastName());
    }

    @Test
    void delete() {
        authorService.delete(1);
        verify(repository, times(1)).deleteById(1);
    }

    private Author author() {
        return new Author(1, "Test", "Testov");
    }

}