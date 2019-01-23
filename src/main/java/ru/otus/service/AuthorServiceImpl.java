package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.entity.Author;
import ru.otus.repository.AuthorRepository;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    public AuthorServiceImpl(AuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public int create(Author author) {
        return repository.create(author);
    }

    @Override
    public void update(Author author) {
        repository.update(author);
    }

    @Override
    public Author getById(int id) {
        return repository.getById(id);
    }

    @Override
    public List<Author> getAll() {
        return repository.getAll();
    }

    @Override
    public void delete(int id) {
        repository.delete(id);
    }
}
