package ru.otus.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.model.Author;
import ru.otus.service.AuthorService;

@ShellComponent
public class AuthorShell {

    private final AuthorService authorService;

    public AuthorShell(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ShellMethod("создаёт автора")
    public String createAuthor(
            @ShellOption(help = "имя автора") String name,
            @ShellOption(defaultValue = "", help = "фамилия автора") String lastName
    ) {
        int id = authorService.create(new Author(name, lastName));
        return "ID автора: " + id;
    }

    @ShellMethod("обновляет автора")
    public void updateAuthor(
            @ShellOption(help = "id автора") int id,
            @ShellOption(defaultValue = "", help = "имя автора") String name,
            @ShellOption(defaultValue = "", help = "фамилия автора") String lastName
    ) {
        authorService.update(new Author(id, name, lastName));
    }

    @ShellMethod("выводит автора по id")
    public String getAuthor(@ShellOption(help = "id автора") int id) {
        Author author = authorService.getById(id);
        if (author == null) {
            return "Автор с указанным ID не найден";
        }
        return getInfo(author);
    }

    @ShellMethod("получает список всех авторов")
    public String getAllAuthors() {
        StringBuilder sb = new StringBuilder();
        for (Author author : authorService.getAll()) {
            sb.append(getInfo(author));
        }
        return sb.toString();
    }

    @ShellMethod("удаляет автора по id")
    public String deleteAuthor(@ShellOption(help = "id автора") int id) {
        if (authorService.delete(id)) {
            return "Автор успешно удалён";
        } else {
            return "Нельзя удалить автора, пока есть связанные с ним книги";
        }
    }

    private String getInfo(Author author) {
        StringBuilder sb = new StringBuilder();
        sb.append(author.getId()).append(" ").append(author.getName()).append(" ").append(author.getLastName()).append("\n");
        return sb.toString();
    }
}
