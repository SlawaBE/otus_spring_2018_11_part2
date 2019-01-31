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
import ru.otus.entity.Comment;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith({MockitoExtension.class})
@DataMongoTest
class CommentRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CommentRepository repository;

    @BeforeEach
    void init() {
        mongoTemplate.dropCollection("books");
        mongoTemplate.dropCollection("comments");
        mongoTemplate.insert(new Book("b1", "Book_Name1", "Book_Summary1", singletonList("Author1"), singletonList("Genre1")));
        mongoTemplate.insert(new Book("b2", "Book_Name2", "Book_Summary2", singletonList("Author2"), singletonList("Genre2")));
        mongoTemplate.insert(new Comment("user1", "text1", "b1"));
        mongoTemplate.insert(new Comment("user2", "text2", "b1"));
    }

    @Test
    void testCreate() {
        Comment expected = comment();
        repository.save(expected);
        Comment actual = getComment(expected.getId());
        assertThat(actual)
                .hasFieldOrPropertyWithValue("userName", "user")
                .hasFieldOrPropertyWithValue("text", "text")
                .hasFieldOrPropertyWithValue("book.id", "b1")
                .hasNoNullFieldsOrProperties();
    }

    @Test
    void testDelete() {
        Comment comment = mongoTemplate.save(comment());
        assertNotNull(getComment(comment.getId()));
        repository.deleteById(comment.getId());
        assertNull(getComment(comment.getId()));
    }

    @Test
    void testFindByBookId() {
        List<Comment> list = repository.findByBookId("b1");
        assertThat(list).asList()
                .hasSize(2);
    }

    @Test
    void testDeleteByBookId() {
        repository.deleteByBookId("b1");
        List<Comment> list = getCommentsByBookId("b1");
        assertThat(list).asList()
                .hasSize(0);
    }

    private Comment getComment(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, Comment.class);
    }

    private List<Comment> getCommentsByBookId(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("book.id").is(id));
        return mongoTemplate.find(query, Comment.class);
    }

    private Comment comment() {
        return new Comment("user", "text", "b1");
    }

}