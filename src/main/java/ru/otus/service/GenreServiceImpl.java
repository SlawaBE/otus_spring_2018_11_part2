package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.dao.GenreDao;
import ru.otus.model.Genre;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao dao;

    public GenreServiceImpl(GenreDao dao) {
        this.dao = dao;
    }

    @Override
    public int create(Genre genre) {
        return dao.create(genre);
    }

    @Override
    public void update(Genre genre) {
        dao.update(genre);
    }

    @Override
    public Genre getById(int id) {
        return dao.getById(id);
    }

    @Override
    public List<Genre> getAll() {
        return dao.getAll();
    }

    @Override
    public boolean delete(int id) {
        return dao.delete(id);
    }
}
