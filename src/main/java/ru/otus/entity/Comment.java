package ru.otus.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

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
    }

    public Comment(String userName, String text, String bookId) {
        this.userName = userName;
        this.sendDate = new Date();
        this.text = text;
        this.book = new Book();
        this.book.setId(bookId);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

}
