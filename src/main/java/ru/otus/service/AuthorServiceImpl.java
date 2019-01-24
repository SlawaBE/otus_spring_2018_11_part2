package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.entity.Author;
import ru.otus.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    public AuthorServiceImpl(AuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public int create(Author author) {
        Author author1 = repository.save(author);
        return author1.getId();
    }

    @Override
    public void update(Author author) {
        repository.save(author);
    }

    @Override
    public Author getById(int id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Author> getAll() {
        List<Author> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }
}
