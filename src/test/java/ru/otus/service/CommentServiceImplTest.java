package ru.otus.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import ru.otus.entity.Comment;
import ru.otus.repository.CommentRepository;

import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
@Import(CommentServiceImpl.class)
class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    @Test
    void testCreate() {
        Comment comment = comment();
        when(commentRepository.save(comment)).thenReturn(comment);
        commentService.create(comment);
        verify(commentRepository, times(1)).save(comment);
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

}