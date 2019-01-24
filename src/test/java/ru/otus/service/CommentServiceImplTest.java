package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.entity.Comment;
import ru.otus.repository.CommentRepository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith({SpringExtension.class, MockitoExtension.class})
@Import(CommentServiceImpl.class)
class CommentServiceImplTest {

    @Autowired
    private CommentServiceImpl commentService;

    @MockBean
    private CommentRepository commentRepository;

    private Comment comment;

    @BeforeEach
    void init() {
        comment = new Comment("user", "text", 1);
    }

    @Test
    void testCreate() {
        when(commentRepository.save(comment)).thenReturn(comment);
        commentService.create(comment);
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void testGetByBookId() {
        commentService.getByBookId(1);
        verify(commentRepository, times(1)).findByBookId(1);
    }

    @Test
    void testDelete() {
        commentService.delete(1);
        verify(commentRepository, times(1)).deleteById(1L);
    }

}