package ru.otus.service;

import ru.otus.entity.Genre;

import java.util.List;

public interface GenreService {

    int create(Genre genre);

    void update(Genre genre);

    Genre getById(int id);

    List<Genre> getAll();

    void delete(int id);
}
