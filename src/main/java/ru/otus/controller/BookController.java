package ru.otus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.entity.Book;
import ru.otus.entity.Comment;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class BookController {

    private final BookService bookService;

    private final CommentService commentService;

    public BookController(BookService bookService, CommentService commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
    }

    @RequestMapping(path = "/", method = GET)
    public String booksList(Model model) {
        model.addAttribute("books", bookService.getAll());
        return "list";
    }

    @RequestMapping(path = "/book/create", method = GET)
    public String createForm() {
        return "create";
    }

    @RequestMapping(path = "/book/view", method = GET)
    public String viewBook(Model model, @RequestParam("id") String id) {
        Book book = bookService.getById(id);
        List<Comment> comments =  commentService.getByBookId(id);
        model.addAttribute("book", book);
        model.addAttribute("comments", comments);
        return "view";
    }

    @RequestMapping(path = "/book/edit", method = GET)
    public String editForm(Model model, @RequestParam("id") String id) {
        Book book = bookService.getById(id);
        model.addAttribute("book", book);
        return "edit";
    }

    @RequestMapping(path = "/book/save", method = POST)
    public String saveBook(Model model, @ModelAttribute Book book) {
        bookService.update(book);
        return "redirect:/book/view?id=" + book.getId();
    }

    @RequestMapping(path = "/book/delete", method = POST)
    public String deleteBook(Model model, @RequestParam("id") String id) {
        bookService.delete(id);
        return "redirect:/";
    }

    @RequestMapping(path="/book/comment", method = POST)
    public String addComment(@ModelAttribute Comment comment) {
        commentService.create(comment);
        return "redirect:/book/view?id=" + comment.getBook().getId();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleError(RuntimeException ex) {
        ex.printStackTrace();
        return ResponseEntity.badRequest().body("Произошла непредвиденная ошибка!<br><a href=\"/\">Вернуться на главную</a>");
    }

}
