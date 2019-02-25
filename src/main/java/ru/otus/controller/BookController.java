package ru.otus.controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.dto.CommentDto;
import ru.otus.entity.Book;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;

@RestController
public class BookController {

    private final BookService bookService;

    private final CommentService commentService;

    public BookController(BookService bookService, CommentService commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
    }

    @GetMapping(path = "/api/books")
    public Flux<Book> booksList() {
        return bookService.getAll();
    }

    @GetMapping(path = "/api/book")
    public Mono<Book> getBook(@RequestParam("id") String id) {
        return bookService.getById(id);
    }

    @PostMapping(path = "/api/book", consumes = "application/json")
    public Mono<Book> saveBook(@RequestBody Book book) {
        return bookService.update(book);
    }

    @DeleteMapping(path = "/api/book")
    public Mono<Void> deleteBook(@RequestParam("id") String id) {
        return bookService.delete(id);
    }

    @PostMapping(path = "/api/book/{id}/comment", consumes = "application/json")
    public Mono<CommentDto> addComment(@RequestBody CommentDto comment) {
        return commentService.create(comment);
    }

    @GetMapping(path = "/api/book/{id}/comments")
    public Flux<CommentDto> getCommentsById(@PathVariable("id") String id) {
        return commentService.getByBookId(id);
    }

}
