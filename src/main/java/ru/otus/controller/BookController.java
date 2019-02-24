package ru.otus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.dto.CommentDto;
import ru.otus.entity.Book;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;

import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    private final CommentService commentService;

    public BookController(BookService bookService, CommentService commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
    }

    @GetMapping(path = "/api/books")
    public List<Book> booksList() {
        return bookService.getAll();
    }

    @GetMapping(path = "/api/book")
    public Book getBook(@RequestParam("id") String id) {
        return bookService.getById(id);
    }

    @PostMapping(path = "/api/book", consumes = "application/json")
    public Book saveBook(@RequestBody Book book) {
        return bookService.update(book);
    }

    @DeleteMapping(path = "/api/book")
    public ResponseEntity deleteBook(@RequestParam("id") String id) {
        bookService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path="/api/book/{id}/comment", consumes = "application/json")
    public CommentDto addComment(@RequestBody CommentDto comment) {
        return commentService.create(comment);
    }

    @GetMapping(path = "/api/book/{id}/comments")
    public List<CommentDto> getCommentsById(@PathVariable("id") String id) {
        return commentService.getByBookId(id);
    }

}
