package ru.otus.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class CommentDto {

    private String userName;

    private String text;

    private String bookId;

    private Date sendDate;

    public CommentDto(String userName, String text, Date sendDate) {
        this.userName = userName;
        this.text = text;
        this.sendDate = sendDate;
    }

}
