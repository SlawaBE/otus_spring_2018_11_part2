package ru.otus.batch.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "authors")
public class AuthorSql {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public AuthorSql(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AuthorSql{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
