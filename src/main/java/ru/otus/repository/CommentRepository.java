package ru.otus.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.entity.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findByBookId(int id);

}
