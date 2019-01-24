package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.entity.Genre;
import ru.otus.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository repository;

    public GenreServiceImpl(GenreRepository repository) {
        this.repository = repository;
    }

    @Override
    public int create(Genre genre) {
        return repository.save(genre).getId();
    }

    @Override
    public void update(Genre genre) {
        repository.save(genre);
    }

    @Override
    public Genre getById(int id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Genre> getAll() {
        List<Genre> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }
}
