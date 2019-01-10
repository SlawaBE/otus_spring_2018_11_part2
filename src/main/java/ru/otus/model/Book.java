package ru.otus.model;

public class Book {

    private int id;

    private String name;

    private String summary;

    private Author author;

    private Genre genre;

    public Book(String name, String summary, Author author, Genre genre) {
        this.name = name;
        this.summary = summary;
        this.author = author;
        this.genre = genre;
    }

    public Book(int id, String name, String summary, Author author, Genre genre) {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.author = author;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

}
