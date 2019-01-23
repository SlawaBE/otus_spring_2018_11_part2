package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.entity.Author;
import ru.otus.repository.AuthorRepository;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@Import(AuthorServiceImpl.class)
class AuthorServiceImplTest {

    @Autowired
    private AuthorServiceImpl authorService;

    @MockBean
    private AuthorRepository repository;

    private Author author;

    @BeforeEach
    void init() {
        author = new Author(1, "Test", "Testov");
    }

    @Test
    void create() {
        authorService.create(author);
        verify(repository, times(1)).create(author);
    }

    @Test
    void update() {
        authorService.update(author);
        verify(repository, times(1)).update(author);
    }

    @Test
    void getById() {
        when(repository.getById(1)).thenReturn(author);
        Author actual = authorService.getById(1);
        verify(repository, times(1)).getById(1);
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", author.getId())
                .hasFieldOrPropertyWithValue("name", author.getName())
                .hasFieldOrPropertyWithValue("lastName", author.getLastName());
    }

    @Test
    void getAll() {
        when(repository.getAll()).thenReturn(Collections.singletonList(author));
        List<Author> list = authorService.getAll();
        verify(repository, times(1)).getAll();
        assertEquals(1, list.size());
        assertThat(list.get(0))
                .hasFieldOrPropertyWithValue("id", author.getId())
                .hasFieldOrPropertyWithValue("name", author.getName())
                .hasFieldOrPropertyWithValue("lastName", author.getLastName());
    }

    @Test
    void delete() {
        authorService.delete(1);
        verify(repository, times(1)).delete(1);
    }

}