package ru.otus.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("books")
public class Book {

    @Id
    private String id;

    private String name;

    private String summary;

    private List<String> authors;

    private List<String> genres;

    public Book() {
    }

    public Book(String name, String summary, List<String> authors, List<String> genres) {
        this.name = name;
        this.summary = summary;
        this.authors = authors;
        this.genres = genres;
    }

    public Book(String id, String name, String summary, List<String> authors, List<String> genres) {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.authors = authors;
        this.genres = genres;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

}
