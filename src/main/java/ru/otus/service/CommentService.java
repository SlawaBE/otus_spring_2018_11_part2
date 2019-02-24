package ru.otus.service;

import ru.otus.dto.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto create(CommentDto comment);

    List<CommentDto> getByBookId(String id);

    void delete(String id);

}
