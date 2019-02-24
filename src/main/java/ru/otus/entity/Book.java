package ru.otus.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document("books")
public class Book {

    @Id
    private String id;

    private String name;

    private String summary;

    private List<String> authors;

    private List<String> genres;

    public Book(String name, String summary, List<String> authors, List<String> genres) {
        this.name = name;
        this.summary = summary;
        this.authors = authors;
        this.genres = genres;
    }

}
