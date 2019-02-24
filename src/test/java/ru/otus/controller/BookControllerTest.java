package ru.otus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.dto.CommentDto;
import ru.otus.entity.Book;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;

import java.util.Date;

import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService commentService;

    @MockBean
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private CommentRepository commentRepository;

    @Test
    void booksList() throws Exception {
        when(bookService.getAll()).thenReturn(singletonList(book()));
        mvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json;charset=UTF-8"));
    }

    @Test
    void getBook() throws Exception {
        when(bookService.getById("id")).thenReturn(book());
        mvc.perform(get("/api/book")
                .param("id", "id"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json;charset=UTF-8"));
    }

    @Test
    void saveBook() throws Exception {
        when(bookService.update(any(Book.class))).thenReturn(book());
        mvc.perform(post("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(book())))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json;charset=UTF-8"));
    }

    @Test
    void deleteBook() throws Exception {
        mvc.perform(delete("/api/book")
                .param("id", "id"))
                .andExpect(status().isOk());
    }

    @Test
    void addComment() throws Exception {
        when(commentService.create(any(CommentDto.class))).thenReturn(commentDto());
        mvc.perform(post("/api/book/id/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(commentDto())))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json;charset=UTF-8"));
    }

    @Test
    void getCommentsById() throws Exception {
        when(commentService.getByBookId("id")).thenReturn(singletonList(commentDto()));
        mvc.perform(get("/api/book/id/comments"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json;charset=UTF-8"));
    }

    private Book book() {
        return new Book("name", "summary", singletonList("author"), singletonList("genre"));
    }

    private CommentDto commentDto() {
        return new CommentDto("userName", "text", new Date());
    }

}
