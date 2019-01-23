package ru.otus.entity;

import javax.persistence.*;

@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private int id;

    @Column(name = "genre_name")
    private String name;

    public Genre() {
    }

    public Genre(int id) {
        this.id = id;
    }

    public Genre(String name) {
        this.name = name;
    }

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
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

}
