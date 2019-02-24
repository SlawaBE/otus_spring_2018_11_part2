package ru.otus.service;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.dto.CommentDto;
import ru.otus.mapper.CommentMapper;
import ru.otus.repository.CommentRepository;

@Component
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    private final CommentMapper mapper;

    public CommentServiceImpl(CommentRepository repository, CommentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<CommentDto> create(CommentDto commentDto) {
        return repository.save(mapper.convert(commentDto)
        ).flatMap(comment -> Mono.just(comment).map(mapper::convert));
    }

    @Override
    public Flux<CommentDto> getByBookId(String id) {
        return repository.findByBookId(id).flatMap(comment -> Flux.just(comment).map(mapper::convert));
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

}
