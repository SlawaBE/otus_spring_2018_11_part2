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
import ru.otus.dao.BookDao;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
class BookServiceImplTest {

    @Autowired
    private BookServiceImpl bookService;

    @MockBean
    private BookDao dao;

    private Book book;

    @BeforeEach
    void init() {
        book = new Book(1, "bookname", "summary", new Author(1, "Test", "Testov"), new Genre(1, "genre"));
    }

    @Test
    void create() {
        bookService.create(book);
        verify(dao, times(1)).create(book);
    }

    @Test
    void update() {
        when(dao.getById(1)).thenReturn(book);
        bookService.update(new Book(1, "book", "desc", new Author(2), new Genre(2)));
        verify(dao, times(1)).update(book);
        assertThat(book)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "book")
                .hasFieldOrPropertyWithValue("summary", "desc")
                .hasFieldOrPropertyWithValue("author.id", 2)
                .hasFieldOrPropertyWithValue("genre.id", 2);
    }

    @Test
    void getById() {
        when(dao.getById(1)).thenReturn(book);
        Book actual = bookService.getById(1);
        verify(dao, times(1)).getById(1);
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
        when(dao.getAll()).thenReturn(singletonList(book));
        List<Book> list = bookService.getAll();
        verify(dao, times(1)).getAll();
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
        verify(dao, times(1)).delete(1);
    }

}