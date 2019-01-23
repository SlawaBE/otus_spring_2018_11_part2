package ru.otus.entity;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int id;

    @Column(name = "book_name")
    private String name;

    @Column(name = "book_summary")
    private String summary;

    @OneToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    public Book() {
    }

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
