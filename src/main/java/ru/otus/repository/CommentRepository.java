package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.entity.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findByBookId(String id);

    void deleteByBookId(String id);

}
