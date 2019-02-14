package ru.otus.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.otus.entity.Book;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    void createForm() throws Exception {
        mvc.perform(get("/book/create"))
                .andExpect(status().isOk());
    }

    @Test
    void viewBook() throws Exception {
        when(bookService.getById("id")).thenReturn(book());
        mvc.perform(get("/book/view")
                .param("id", "id"))
                .andExpect(status().isOk());
    }

    @Test
    void saveBook() throws Exception {
        mvc.perform(
                post("/book/save")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(bookAsMap()))
                .andExpect(status().isFound());
    }

    @Test
    void deleteBook() throws Exception {
        mvc.perform(post("/book/delete")
                .param("id", "id"))
                .andExpect(status().isFound());
    }

    @Test
    void addComment() throws Exception {
        mvc.perform(post("/book/comment")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(commentAsMap()))
                .andExpect(status().isFound());
    }

    private Book book() {
        return new Book("name", "summary", singletonList("author"), singletonList("genre"));
    }

    private MultiValueMap<String, String> bookAsMap() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("name", "title");
        map.add("summary", "summary");
        return map;
    }

    private MultiValueMap<String, String> commentAsMap() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("userName", "user");
        map.add("text", "comment");
        map.add("book.id", "bookId");
        return map;
    }

}
