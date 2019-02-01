package ru.otus.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.entity.Book;
import ru.otus.service.BookService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class BookShell {

    private final BookService bookService;

    @Autowired
    public BookShell(BookService bookService) {
        this.bookService = bookService;
    }

    @ShellMethod("создаёт книгу")
    public String createBook(
            @ShellOption(help = "имя книги") String name,
            @ShellOption(help = "описание книги") String summary,
            @ShellOption(help = "список авторов через запятую") String authors,
            @ShellOption(help = "список жанров через запятую") String genres
    ) {
        String id = bookService.create(new Book(name, summary, convertToList(authors), convertToList(genres)));
        return "ID книги: " + id;
    }

    @ShellMethod("обновляет книгу")
    public void updateBook(
            @ShellOption(help = "id книги") String id,
            @ShellOption(help = "имя книги") String name,
            @ShellOption(help = "описание книги") String summary,
            @ShellOption(help = "список авторов через запятую") String authors,
            @ShellOption(help = "список жанров через запятую") String genres
    ) {
        bookService.update(new Book(id, name, summary, convertToList(authors), convertToList(genres)));
    }


    @ShellMethod("выводит книгу по id")
    public String getBook(@ShellOption(help = "id книги") String id) {
        Book book = bookService.getById(id);
        if (book == null) {
            return "Книга с указанным ID не найдена";
        }
        return getInfo(book);

    }

    @ShellMethod("выводит список книг")
    public String getAllBooks() {
        StringBuilder sb = new StringBuilder();
        for (Book book : bookService.getAll()) {
            sb.append(getInfo(book));
            sb.append("----------------\n");
        }
        return sb.toString();
    }

    @ShellMethod("удалает книгу по id")
    public void deleteBook(@ShellOption(help = "id книги") String id) {
        bookService.delete(id);
    }

    @ShellMethod("найти книги по автору")
    public String findBookByAuthor(@ShellOption(help = "имя автора") String name) {
        StringBuilder sb = new StringBuilder();
        for (Book book : bookService.findByAuthor(name)) {
            sb.append(getInfo(book));
            sb.append("----------------\n");
        }
        return sb.toString();
    }

    @ShellMethod("найти книги по жанру")
    public String findBookByGenre(@ShellOption(help = "имя автора") String genre) {
        StringBuilder sb = new StringBuilder();
        for (Book book : bookService.findByGenre(genre)) {
            sb.append(getInfo(book));
            sb.append("----------------\n");
        }
        return sb.toString();
    }

    private List<String> convertToList(String str) {
        return Arrays.stream(str.split(",")).map(String::trim).collect(Collectors.toList());
    }

    private String getInfo(Book book) {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(book.getId()).append("\n")
                .append(book.getName()).append("\n");

        sb.append(book.getAuthors().size() == 1 ? "Автор:\n" : "Авторы:\n");
        for (String author : book.getAuthors()) {
            sb.append("\t").append(author).append("\n");
        }

        sb.append("Жанр: ").append(String.join(", ", book.getGenres())).append("\n");

        sb.append("Описание: ").append(book.getSummary()).append("\n");
        return sb.toString();
    }
}
