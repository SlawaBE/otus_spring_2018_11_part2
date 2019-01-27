package ru.otus.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.entity.Genre;
import ru.otus.service.GenreService;

@ShellComponent
public class GenreShell {

    private final GenreService genreService;

    public GenreShell(GenreService genreService) {
        this.genreService = genreService;
    }

    @ShellMethod("создаёт жанр")
    public String createGenre(@ShellOption(help = "название жанра") String name) {
        int id = genreService.create(new Genre(name));
        return "ID жанра: " + id;
    }

    @ShellMethod("обновляет жанр")
    public void updateGenre(
            @ShellOption(help = "id жанра") int id,
            @ShellOption(defaultValue = "", help = "название жанра") String name
    ) {
        genreService.update(new Genre(id, name));
    }

    @ShellMethod("выводит жанр по id")
    public String getGenre(@ShellOption(help = "id жанра") int id) {
        Genre genre = genreService.getById(id);
        return getInfo(genre);
    }

    @ShellMethod("выводит список всех жанров")
    public String getAllGenres() {
        StringBuilder sb = new StringBuilder();
        for (Genre genre : genreService.getAll()) {
            sb.append(getInfo(genre));
        }
        return sb.toString();
    }

    @ShellMethod("удаляет жанр")
    public void deleteGenre(@ShellOption(help = "id жанра") int id) {
        genreService.delete(id);
    }

    private String getInfo(Genre genre) {
        StringBuilder sb = new StringBuilder();
        sb.append(genre.getId()).append(" ").append(genre.getName()).append("\n");
        return sb.toString();
    }
}
