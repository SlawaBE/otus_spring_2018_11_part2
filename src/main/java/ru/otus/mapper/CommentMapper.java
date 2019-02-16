package ru.otus.mapper;

import org.springframework.stereotype.Component;
import ru.otus.dto.CommentDto;
import ru.otus.entity.Comment;

@Component
public class CommentMapper {

    public Comment convert(CommentDto comment) {
        return new Comment(
                comment.getUserName(),
                comment.getText(),
                comment.getBookId()
        );
    }

    public CommentDto convert(Comment comment) {
        return new CommentDto(
                comment.getUserName(),
                comment.getText(),
                comment.getSendDate()
        );
    }

}
