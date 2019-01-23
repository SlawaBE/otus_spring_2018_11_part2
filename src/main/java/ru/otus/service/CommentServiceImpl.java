package ru.otus.service;

import org.springframework.stereotype.Component;
import ru.otus.entity.Comment;
import ru.otus.repository.CommentRepository;

import java.util.List;

@Component
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(Comment comment) {
        repository.create(comment);
    }

    @Override
    public List<Comment> getByBookId(int id) {
        return repository.getByBookId(id);
    }

    @Override
    public void delete(long id) {
        repository.delete(id);
    }
}
