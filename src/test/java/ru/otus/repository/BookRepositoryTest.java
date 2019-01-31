package ru.otus.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.entity.Book;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith({MockitoExtension.class})
@DataMongoTest
class BookRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private BookRepository repository;

    @BeforeEach
    void init() {
        mongoTemplate.dropCollection("books");
        mongoTemplate.insert(new Book("id1", "Book_Name1", "Book_Summary1", singletonList("Author1"), singletonList("Genre1")));
        mongoTemplate.insert(new Book("id2", "Book_Name2", "Book_Summary2", singletonList("Author2"), singletonList("Genre2")));
    }

    @Test
    void createTest() {
        Book expected = book();
        String id = repository.save(expected).getId();
        Book actual = getBook(id);
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("name", "book")
                .hasFieldOrPropertyWithValue("summary", "summary")
                .hasFieldOrPropertyWithValue("authors", singletonList("author"))
                .hasFieldOrPropertyWithValue("genres", singletonList("genre"));
    }

    @Test
    void updateTest() {
        Book expected = new Book("id1", "Test", "description", getAuthors(), getGenres());
        repository.save(expected);
        Book actual = getBook("id1");
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", "id1")
                .hasFieldOrPropertyWithValue("name", "Test")
                .hasFieldOrPropertyWithValue("summary", "description")
                .hasFieldOrPropertyWithValue("authors", getAuthors())
                .hasFieldOrPropertyWithValue("genres", getGenres());
    }

    private List<String> getAuthors() {
        List<String> authors = new ArrayList<>();
        authors.add("author1");
        authors.add("author2");
        return authors;
    }

    private List<String> getGenres() {
        List<String> authors = new ArrayList<>();
        authors.add("genre1");
        authors.add("genre2");
        return authors;
    }

    @Test
    void getAllTest() {
        List<Book> books = repository.findAll();
        assertThat(books).asList().hasSize(2);
    }

    @Test
    void getByIdTest() {
        Book book = repository.findById("id1").get();
        assertThat(book)
                .hasFieldOrPropertyWithValue("id", "id1")
                .hasFieldOrPropertyWithValue("name", "Book_Name1")
                .hasFieldOrPropertyWithValue("summary", "Book_Summary1")
                .hasFieldOrPropertyWithValue("authors", singletonList("Author1"))
                .hasFieldOrPropertyWithValue("genres", singletonList("Genre1"));
    }

    @Test
    void deleteTest() {
        assertNotNull(getBook("id1"));
        repository.deleteById("id1");
        assertNull(getBook("id1"));
    }

    @Test
    void testFindByAuthor() {
        List<Book> list = repository.findByAuthor("Author1");
        assertThat(list).asList().hasSize(1);
    }

    @Test
    void testFindByGenre() {
        List<Book> list = repository.findByGenre("Genre1");
        assertThat(list).asList().hasSize(1);
    }

    private Book getBook(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, Book.class);
    }

    private Book book() {
        return new Book("book", "summary", singletonList("author"), singletonList("genre"));
    }

}