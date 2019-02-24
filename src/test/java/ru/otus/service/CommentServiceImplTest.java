package ru.otus.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import ru.otus.dto.CommentDto;
import ru.otus.entity.Comment;
import ru.otus.mapper.CommentMapper;
import ru.otus.repository.CommentRepository;

import java.util.Date;

import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
@Import(CommentServiceImpl.class)
class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    @Spy
    private CommentMapper mapper;

    @Test
    void testCreate() {
        when(commentRepository.save(any(Comment.class))).thenReturn(comment());
        commentService.create(commentDto());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void testGetByBookId() {
        commentService.getByBookId("bookId");
        verify(commentRepository, times(1)).findByBookId("bookId");
    }

    @Test
    void testDelete() {
        commentService.delete("id");
        verify(commentRepository, times(1)).deleteById("id");
    }

    private Comment comment() {
        return new Comment("user", "text", "bookId");
    }

    private CommentDto commentDto() {
        return new CommentDto("user", "text", new Date());
    }

}