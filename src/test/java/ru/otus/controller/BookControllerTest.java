package ru.otus.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
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
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
class BookControllerTest {

    @Autowired
    private ApplicationContext applicationContext;

    @MockBean
    private CommentService commentService;

    @MockBean
    private BookService bookService;

    private WebTestClient webTestClient;

    @BeforeEach
    void init() {
        webTestClient = WebTestClient.bindToApplicationContext(applicationContext)
                .apply(springSecurity())
                .configureClient()
                .build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void booksList() throws Exception {
        when(bookService.getAll()).thenReturn(Flux.fromIterable(singletonList(book())));
        webTestClient.get().uri("/api/books")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getBook() throws Exception {
        when(bookService.getById("id")).thenReturn(Mono.fromSupplier(this::book));
        webTestClient.get().uri("/api/book?id=id")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void saveBook() throws Exception {
        when(bookService.update(any(Book.class), any(UserDetails.class))).thenReturn(Mono.fromSupplier(this::book));
        webTestClient.post().uri("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .syncBody(book())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteBook() throws Exception {
        webTestClient.delete().uri("/api/book?id=id")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
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
    @WithMockUser(roles = "ADMIN")
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
