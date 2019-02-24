package ru.otus.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import ru.otus.entity.Book;
import ru.otus.repository.BookRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
@Import(BookServiceImpl.class)
class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository repository;

    @Test
    void update() {
        Book book = book();
        bookService.update(book);
        verify(repository, times(1)).save(book);
    }

    @Test
    void getById() {
        Book book = book();
        when(repository.findById("id")).thenReturn(Optional.of(book));
        Book actual = bookService.getById("id");
        verify(repository, times(1)).findById("id");

    }

    @Test
    void getAll() {
        Book book = book();
        when(repository.findAll()).thenReturn(singletonList(book));
        List<Book> list = bookService.getAll();
        verify(repository, times(1)).findAll();
    }

    @Test
    void delete() {
        bookService.delete("id");
        verify(repository, times(1)).deleteById("id");
    }

    private Book book() {
        return new Book("id", "bookName", "summary", singletonList("author"), singletonList("genre"));
    }

}