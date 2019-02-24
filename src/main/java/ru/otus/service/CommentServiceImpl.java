package ru.otus.service;

import org.springframework.stereotype.Component;
import ru.otus.dto.CommentDto;
import ru.otus.mapper.CommentMapper;
import ru.otus.repository.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    private final CommentMapper mapper;

    public CommentServiceImpl(CommentRepository repository, CommentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto create(CommentDto comment) {
        return mapper.convert(repository.save(mapper.convert(comment)));
    }

    @Override
    public List<CommentDto> getByBookId(String id) {
        return repository.findByBookId(id).stream()
                .map(mapper::convert)
                .collect(Collectors.toList()
        );

    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

}
