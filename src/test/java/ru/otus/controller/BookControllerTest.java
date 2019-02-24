package ru.otus.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.dto.CommentDto;
import ru.otus.entity.Book;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;

import java.util.Date;

import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(BookController.class)
class BookControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CommentService commentService;

    @MockBean
    private BookService bookService;

    @Test
    void booksList() throws Exception {
        when(bookService.getAll()).thenReturn(Flux.fromIterable(singletonList(book())));
        webTestClient.get().uri("/api/books")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8);
    }

    @Test
    void getBook() throws Exception {
        when(bookService.getById("id")).thenReturn(Mono.fromSupplier(this::book));
        webTestClient.get().uri("/api/book?id=id")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8);
    }

    @Test
    void saveBook() throws Exception {
        when(bookService.update(any(Book.class))).thenReturn(Mono.fromSupplier(this::book));
        webTestClient.post().uri("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .syncBody(book())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8);
    }

    @Test
    void deleteBook() throws Exception {
        webTestClient.delete().uri("/api/book?id=id")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void addComment() throws Exception {
        when(commentService.create(any(CommentDto.class))).thenReturn(Mono.fromSupplier(this::commentDto));
        webTestClient.post().uri("/api/book/id/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .syncBody(commentDto())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8);
    }

    @Test
    void getCommentsById() throws Exception {
        when(commentService.getByBookId("id")).thenReturn(Flux.fromIterable(singletonList(commentDto())));
        webTestClient.get().uri("/api/book/id/comments")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8);
    }

    private Book book() {
        return new Book("name", "summary", singletonList("author"), singletonList("genre"));
    }

    private CommentDto commentDto() {
        return new CommentDto("userName", "text", new Date());
    }

}
