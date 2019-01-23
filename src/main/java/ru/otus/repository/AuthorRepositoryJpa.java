package ru.otus.repository;

import org.springframework.stereotype.Repository;
import ru.otus.entity.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;


@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    public AuthorRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public int create(Author author) {
        em.persist(author);
        return author.getId();
    }

    @Override
    @Transactional
    public void update(Author author) {
        em.merge(author);
    }

    @Override
    public Author getById(int id) {
        TypedQuery<Author> query = em.createQuery("SELECT a from Author a where id = :id", Author.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public List<Author> getAll() {
        TypedQuery<Author> query = em.createQuery("SELECT a from Author a", Author.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void delete(int id) {
        Query query = em.createQuery("DELETE from Author where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
