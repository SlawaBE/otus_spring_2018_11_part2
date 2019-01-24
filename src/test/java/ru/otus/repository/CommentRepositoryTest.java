package ru.otus.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.entity.Comment;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest(properties = "spring.datasource.data=testdata.sql")
@Transactional
class CommentRepositoryTest {

    @Autowired
    private CommentRepository repository;

    @Test
    void testCreate() {
        repository.save(new Comment("user", "text", 2));
        List<Comment> comments = repository.findByBookId(2);
        assertThat(comments.get(0))
                .hasFieldOrPropertyWithValue("userName", "user")
                .hasFieldOrPropertyWithValue("text", "text")
                .hasFieldOrPropertyWithValue("book.id", 2)
                .hasNoNullFieldsOrProperties();
    }

    @Test
    void testDelete() {
        Comment comment = new Comment("user", "delete test", 2);
        repository.save(comment);
        long countBefore = repository.findByBookId(2).size();
        repository.deleteById(comment.getId());
        long countAfter = repository.findByBookId(2).size();
        assertThat(countAfter).isEqualTo(countBefore - 1);
    }

}