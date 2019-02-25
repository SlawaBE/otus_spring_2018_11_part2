package ru.otus.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.dto.CommentDto;

public interface CommentService {

    Mono<CommentDto> create(CommentDto comment);

    Flux<CommentDto> getByBookId(String id);

    void delete(String id);

}
