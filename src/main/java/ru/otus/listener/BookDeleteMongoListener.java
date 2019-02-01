package ru.otus.listener;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.entity.Book;
import ru.otus.repository.CommentRepository;

@Component
public class BookDeleteMongoListener extends AbstractMongoEventListener<Book> {

    private final CommentRepository commentRepository;

    public BookDeleteMongoListener(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void onAfterDelete(AfterDeleteEvent<Book> event) {
        String id = event.getSource().get("_id").toString();
        commentRepository.deleteByBookId(id);
    }

}
