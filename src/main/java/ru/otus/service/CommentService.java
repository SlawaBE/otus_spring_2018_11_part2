package ru.otus.service;

import ru.otus.entity.Comment;

import java.util.List;

public interface CommentService {

    void create(Comment comment);

    List<Comment> getByBookId(int id);

    void delete(long id);

}
