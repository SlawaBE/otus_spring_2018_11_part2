package ru.otus.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.entity.Comment;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@DataJpaTest(properties = "spring.datasource.data=testdata.sql")
@Transactional
class CommentRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CommentRepository repository;

    @Test
    void testCreate() {
        Comment expected = new Comment("user", "text", 2);
        repository.save(expected);
        Comment actual = getComment(expected.getId());
        assertThat(actual)
                .hasFieldOrPropertyWithValue("userName", "user")
                .hasFieldOrPropertyWithValue("text", "text")
                .hasFieldOrPropertyWithValue("book.id", 2)
                .hasNoNullFieldsOrProperties();
    }

    @Test
    void testFindByBookId() {
        List<Comment> comments = repository.findByBookId(1);
        assertThat(comments).asList().hasSize(2);
    }

    @Test
    void testDelete() {
        assertNotNull(getComment(1));
        repository.deleteById(1L);
        assertThrows(NoResultException.class, () -> getComment(1L));
    }

    private Comment getComment(long id) {
        TypedQuery<Comment> query = em.getEntityManager().createQuery("SELECT c from Comment c where id = :id", Comment.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

}