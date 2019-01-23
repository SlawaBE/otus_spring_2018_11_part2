package ru.otus.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.entity.Author;
import ru.otus.entity.Book;
import ru.otus.entity.Genre;
import ru.otus.service.BookService;

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
            @ShellOption(help = "id автора") int authorId,
            @ShellOption(help = "id жанра") int genreId
    ) {
        Author author = new Author(authorId);
        Genre genre = new Genre(genreId);
        int id = bookService.create(new Book(name, summary, author, genre));
        return "ID книги: " + id;
    }

    @ShellMethod("обновляет книгу")
    public void updateBook(
            @ShellOption(help = "id книги") int id,
            @ShellOption(defaultValue = "", help = "имя книги") String name,
            @ShellOption(defaultValue = "", help = "описание книги") String summary,
            @ShellOption(defaultValue = "0", help = "id автора") int authorId,
            @ShellOption(defaultValue = "0", help = "id жанра") int genreId
    ) {
        Author author = null;
        if (authorId != 0) {
            author = new Author(authorId);
        }
        Genre genre = null;
        if (genreId != 0) {
            genre = new Genre(genreId);
        }
        bookService.update(new Book(id, name, summary, author, genre));
    }

    @ShellMethod("выводит книгу по id")
    public String getBook(@ShellOption(help = "id книги") int id) {
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
    public void deleteBook(@ShellOption(help = "id книги") int id) {
        bookService.delete(id);
    }

    private String getInfo(Book book) {
        StringBuilder sb = new StringBuilder();
        Author author = book.getAuthor();
        Genre genre = book.getGenre();
        sb.append("ID: ").append(book.getId()).append("\n")
                .append(book.getName()).append("\n")
                .append("Автор: ").append(author.getName()).append(" ").append(author.getLastName()).append("\n")
                .append("\tID автора: ").append(author.getId()).append("\n")
                .append("Жанр: ").append(genre.getName()).append("\n")
                .append("\tID жанра: ").append(genre.getId()).append("\n")
                .append("Описание: ").append(book.getSummary()).append("\n");
//        book.getComments().forEach(sb::append);
        return sb.toString();
    }
}
