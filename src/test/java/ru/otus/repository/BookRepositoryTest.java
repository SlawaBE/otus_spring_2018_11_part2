package ru.otus.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        String id = repository.save(expected).block().getId();
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
        Book expected = new Book("id1", "Test", "description", authors(), genres());
        repository.save(expected).block();
        Book actual = getBook("id1");
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", "id1")
                .hasFieldOrPropertyWithValue("name", "Test")
                .hasFieldOrPropertyWithValue("summary", "description")
                .hasFieldOrPropertyWithValue("authors", authors())
                .hasFieldOrPropertyWithValue("genres", genres());
    }

    @Test
    void getAllTest() {
        List<Book> books = repository.findAll().collectList().block();
        assertThat(books).asList().hasSize(2);
    }

    @Test
    void getByIdTest() {
        Book book = repository.findById("id1").block();
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
        repository.deleteById("id1").block();
        assertNull(getBook("id1"));
    }

    @Test
    void testFindByAuthor() {
        List<Book> list = repository.findByAuthor("Author1").collectList().block();
        assertThat(list).asList().hasSize(1);
    }

    @Test
    void testFindByGenre() {
        List<Book> list = repository.findByGenresContaining("Genre1").collectList().block();
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

    private List<String> authors() {
        List<String> authors = new ArrayList<>();
        authors.add("author1");
        authors.add("author2");
        return authors;
    }

    private List<String> genres() {
        List<String> authors = new ArrayList<>();
        authors.add("genre1");
        authors.add("genre2");
        return authors;
    }


}