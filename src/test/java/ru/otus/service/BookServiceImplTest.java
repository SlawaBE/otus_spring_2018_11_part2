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
import ru.otus.entity.Book;
import ru.otus.entity.Genre;
import ru.otus.repository.BookRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@Import(BookServiceImpl.class)
class BookServiceImplTest {

    @Autowired
    private BookServiceImpl bookService;

    @MockBean
    private BookRepository repository;

    private Book book;

    @BeforeEach
    void init() {
        book = new Book(1, "bookname", "summary", new Author(1, "Test", "Testov"), new Genre(1, "genre"));
    }

    @Test
    void create() {
        when(repository.save(book)).thenReturn(book);
        bookService.create(book);
        verify(repository, times(1)).save(book);
    }

    @Test
    void update() {
        bookService.update(book);
        verify(repository, times(1)).save(book);
    }

    @Test
    void getById() {
        when(repository.findById(1)).thenReturn(Optional.of(book));
        Book actual = bookService.getById(1);
        verify(repository, times(1)).findById(1);
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "bookname")
                .hasFieldOrPropertyWithValue("summary", "summary")
                .hasFieldOrPropertyWithValue("author.id", 1)
                .hasFieldOrPropertyWithValue("author.name", "Test")
                .hasFieldOrPropertyWithValue("author.lastName", "Testov")
                .hasFieldOrPropertyWithValue("genre.id", 1)
                .hasFieldOrPropertyWithValue("genre.name", "genre");
    }

    @Test
    void getAll() {
        when(repository.findAll()).thenReturn(singletonList(book));
        List<Book> list = bookService.getAll();
        verify(repository, times(1)).findAll();
        assertEquals(1, list.size());
        assertThat(list.get(0))
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "bookname")
                .hasFieldOrPropertyWithValue("summary", "summary")
                .hasFieldOrPropertyWithValue("author.id", 1)
                .hasFieldOrPropertyWithValue("author.name", "Test")
                .hasFieldOrPropertyWithValue("author.lastName", "Testov")
                .hasFieldOrPropertyWithValue("genre.id", 1)
                .hasFieldOrPropertyWithValue("genre.name", "genre");
    }

    @Test
    void delete() {
        bookService.delete(1);
        verify(repository, times(1)).deleteById(1);
    }

}