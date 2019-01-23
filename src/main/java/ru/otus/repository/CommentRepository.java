package ru.otus.repository;

import ru.otus.entity.Comment;

import java.util.List;

public interface CommentRepository {

    void create(Comment comment);

    List<Comment> getByBookId(int id);

    void delete(long id);
}
