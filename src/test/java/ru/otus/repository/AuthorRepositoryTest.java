package ru.otus.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.entity.Author;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest(properties = "spring.datasource.data=testdata.sql")
@Transactional
class AuthorRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private AuthorRepository repositoryJpa;

    @Test
    void createTest() {
        Author expected = new Author("Test", "Testov");
        int id = repositoryJpa.save(expected).getId();
        Author actual = getAuthor(id);
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("name", "Test")
                .hasFieldOrPropertyWithValue("lastName", "Testov");
    }

    @Test
    void updateTest() {
        Author expected = new Author(1, "Test", "Testov");
        repositoryJpa.save(expected);
        Author actual = getAuthor(1);
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "Test")
                .hasFieldOrPropertyWithValue("lastName", "Testov");
    }

    @Test
    void getAllTest() {
        List<Author> authors = new ArrayList<>();
        repositoryJpa.findAll().forEach(authors::add);
        assertEquals(authors.size(), 3);
    }

    @Test
    void getByIdTest() {
        Author author = repositoryJpa.findById(1).get();
        assertThat(author)
                .hasFieldOrPropertyWithValue("id", 1)
                .hasFieldOrPropertyWithValue("name", "Name1")
                .hasFieldOrPropertyWithValue("lastName", "LastName1");
    }

    @Test
    void deleteTest() {
        assertNotNull(getAuthor(3));
        repositoryJpa.deleteById(3);
        assertThrows(NoResultException.class, () -> getAuthor(3));
    }

    private Author getAuthor(int id) {
        TypedQuery<Author> query = em.getEntityManager().createQuery("SELECT a from Author a where id = :id", Author.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
}