package ru.otus.service;

import ru.otus.entity.Comment;

import java.util.List;

public interface CommentService {

    void create(Comment comment);

    List<Comment> getByBookId(String id);

    void delete(String id);

}
