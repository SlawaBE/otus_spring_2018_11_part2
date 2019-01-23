package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.entity.Genre;
import ru.otus.repository.GenreRepository;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository repository;

    public GenreServiceImpl(GenreRepository repository) {
        this.repository = repository;
    }

    @Override
    public int create(Genre genre) {
        return repository.create(genre);
    }

    @Override
    public void update(Genre genre) {
        repository.update(genre);
    }

    @Override
    public Genre getById(int id) {
        return repository.getById(id);
    }

    @Override
    public List<Genre> getAll() {
        return repository.getAll();
    }

    @Override
    public void delete(int id) {
        repository.delete(id);
    }
}
