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
@Table(name = "genres")
public class GenreSql {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public GenreSql(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GenreSql{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
