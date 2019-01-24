package ru.otus.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.entity.Genre;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest(properties = "spring.datasource.data=testdata.sql")
@Transactional
class GenreRepositoryTest {

    @Autowired
    private GenreRepository repository;

    @Test
    void createTest() {
        Genre expected = new Genre("Test");
        int id = repository.save(expected).getId();
        Genre actual = repository.findById(id).get();
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("name", "Test");
    }

    @Test
    void updateTest() {
        Genre expected = new Genre(1, "Test");
        repository.save(expected);
        Genre actual = repository.findById(1).get();
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "Test");
    }

    @Test
    void getAllTest() {
        List<Genre> genres = new ArrayList<>();
        repository.findAll().forEach(genres::add);
        assertEquals(genres.size(), 3);
    }

    @Test
    void getByIdTest() {
        Genre genre = repository.findById(1).get();
        assertThat(genre)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "Genre1");
    }

    @Test
    void deleteTest() {
        assertTrue(repository.findById(3).isPresent());
        repository.deleteById(3);
        assertFalse(repository.findById(3).isPresent());
    }

}