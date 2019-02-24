package ru.otus.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
public @Document("comments")
class Comment {

    @Id
    private String id;

    private String userName;

    private Date sendDate;

    private String text;

    @DBRef
    private Book book;

    public Comment() {
        this.sendDate = new Date();
    }

    public Comment(String userName, String text, String bookId) {
        this.userName = userName;
        this.sendDate = new Date();
        this.text = text;
        this.book = new Book();
        this.book.setId(bookId);
    }

}
