package ru.otus.batch.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "books")
public class BookSql {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String summary;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<AuthorSql> authors;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<GenreSql> genres;

    public BookSql(String name, String summary) {
        this.name = name;
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "BookSql{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", summary='" + summary + '\'' +
                ", authors=" + authors +
                ", genres=" + genres +
                '}';
    }
}
