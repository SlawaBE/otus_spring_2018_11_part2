package ru.otus.dto;

import java.util.Date;

public class CommentDto {

    private String userName;

    private String text;

    private String bookId;

    private Date sendDate;

    public CommentDto() {
    }

    public CommentDto(String userName, String text, Date sendDate) {
        this.userName = userName;
        this.text = text;
        this.sendDate = sendDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
}
