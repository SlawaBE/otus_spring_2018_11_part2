package ru.otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBookId(int id);

}
