package ru.otus.controller;

import org.springframework.web.bind.annotation.*;
import ru.otus.dto.CommentDto;
import ru.otus.service.CommentService;

import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(path="/api/comment", consumes = "application/json", produces = "application/json")
    public CommentDto addComment(@RequestBody CommentDto comment) {
        return commentService.create(comment);
    }

    @GetMapping(path = "/api/comments", produces = "application/json")
    public List<CommentDto> getCommentsById(@RequestParam("bookId") String bookId) {
        return commentService.getByBookId(bookId);
    }

}
