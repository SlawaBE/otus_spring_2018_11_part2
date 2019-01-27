package ru.otus.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "sendDate")
    private Date sendDate;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "book.id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Book book;

    public Comment() {
    }

    public Comment(String userName, String text, int bookId) {
        this.userName = userName;
        this.sendDate = new Date();
        this.text = text;
        this.book = new Book();
        this.book.setId(bookId);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
