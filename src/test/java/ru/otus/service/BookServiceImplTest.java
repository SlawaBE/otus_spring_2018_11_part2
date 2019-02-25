package ru.otus.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.entity.Book;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
@Import(BookServiceImpl.class)
class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CommentRepository commentRepository;

    @Test
    void update() {
        Book book = book();
        bookService.update(book);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void getById() {
        when(bookRepository.findById("id")).thenReturn(Mono.fromSupplier(this::book));
        bookService.getById("id");
        verify(bookRepository, times(1)).findById("id");

    }

    @Test
    void getAll() {
        when(bookRepository.findAll()).thenReturn(Flux.fromIterable(singletonList(book())));
        bookService.getAll();
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void delete() {
        when(bookRepository.deleteById("id")).thenReturn(Mono.empty());
        when(commentRepository.deleteByBookId("id")).thenReturn(Mono.empty());
        bookService.delete("id");
        verify(bookRepository, times(1)).deleteById("id");
        verify(commentRepository, times(1)).deleteByBookId("id");
    }

    private Book book() {
        return new Book("id", "bookName", "summary", singletonList("author"), singletonList("genre"));
    }

}