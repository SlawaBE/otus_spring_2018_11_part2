package ru.otus.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.entity.Author;
import ru.otus.entity.Book;
import ru.otus.entity.Genre;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest(properties = "spring.datasource.data=testdata.sql")
@Transactional
class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Test
    void createTest() {
        Book expected = new Book("Test", "description", new Author(1, null, "lastname"), new Genre(1, null));
        int id = repository.save(expected).getId();
        Book actual = repository.findById(id).get();
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("name", "Test")
                .hasFieldOrPropertyWithValue("summary", "description")
                .hasFieldOrPropertyWithValue("author.id", 1)
                .hasFieldOrPropertyWithValue("genre.id", 1);
    }

    @Test
    void updateTest() {
        Book expected = new Book(1, "Test", "description", new Author(1, "name", "lastname"), new Genre(1, "name"));
        repository.save(expected);
        Book actual = repository.findById(1).get();
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "Test")
                .hasFieldOrPropertyWithValue("summary", "description")
                .hasFieldOrPropertyWithValue("author.id", 1)
                .hasFieldOrPropertyWithValue("genre.id", 1);
    }

    @Test
    void getAllTest() {
        List<Book> books = new ArrayList<>();
        repository.findAll().forEach(books::add);
        assertEquals(books.size(), 2);
    }

    @Test
    void getByIdTest() {
        Book book = repository.findById(1).get();
        assertThat(book)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "Book_Name1")
                .hasFieldOrPropertyWithValue("summary", "Book_Summary1")
                .hasFieldOrPropertyWithValue("author.id", 1)
                .hasFieldOrPropertyWithValue("author.name", "Name1")
                .hasFieldOrPropertyWithValue("author.lastName", "LastName1")
                .hasFieldOrPropertyWithValue("genre.id", 1)
                .hasFieldOrPropertyWithValue("genre.name", "Genre1");
    }

    @Test
    void deleteTest() {
        assertTrue(repository.findById(1).isPresent());
        repository.deleteById(1);
        assertFalse(repository.findById(1).isPresent());
    }

}