package ru.otus.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.entity.Book;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService commentService;

    @MockBean
    private BookService bookService;

    @Test
    public void testBooksList() throws Exception {
        when(bookService.getAll()).thenReturn(singletonList(book()));
        mvc.perform(get("/")).andExpect(status().isOk());
    }

    private Book book() {
        return new Book("id", "bookName", "summary", singletonList("author"), singletonList("genre"));
    }

}