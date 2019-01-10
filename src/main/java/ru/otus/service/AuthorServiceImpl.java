package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.dao.AuthorDao;
import ru.otus.model.Author;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao dao;

    public AuthorServiceImpl(AuthorDao dao) {
        this.dao = dao;
    }

    @Override
    public int create(Author author) {
        return dao.create(author);
    }

    @Override
    public void update(Author author) {
        Author oldAuthor = dao.getById(author.getId());
        oldAuthor.setName(author.getName().isEmpty() ? oldAuthor.getName() : author.getName());
        oldAuthor.setLastName(author.getLastName().isEmpty() ? oldAuthor.getLastName() : author.getLastName());
        dao.update(oldAuthor);
    }

    @Override
    public Author getById(int id) {
        return dao.getById(id);
    }

    @Override
    public List<Author> getAll() {
        return dao.getAll();
    }

    @Override
    public boolean delete(int id) {
        return dao.delete(id);
    }
}
