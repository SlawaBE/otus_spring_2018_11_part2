package ru.otus.controller;

import org.springframework.web.bind.annotation.*;
import ru.otus.entity.Book;
import ru.otus.service.BookService;

import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(path = "/api/books", produces = "application/json")
    public List<Book> booksList() {
        return bookService.getAll();
    }

    @GetMapping(path = "/api/book", produces = "application/json")
    public Book getBook(@RequestParam("id") String id) {
        return bookService.getById(id);
    }

    @PostMapping(path = "/api/book", consumes = "application/json", produces = "application/json")
    public Book saveBook(@RequestBody Book book) {
        return bookService.update(book);
    }

    @DeleteMapping(path = "/api/book", produces = "application/json")
    public void deleteBook(@RequestParam("id") String id) {
        bookService.delete(id);
    }

}
