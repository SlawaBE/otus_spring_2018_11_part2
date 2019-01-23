package ru.otus.repository;

import org.springframework.stereotype.Repository;
import ru.otus.entity.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;


@Repository
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public int create(Genre genre) {
        em.persist(genre);
        return genre.getId();
    }

    @Override
    @Transactional
    public void update(Genre genre) {
        em.merge(genre);
    }

    @Override
    public Genre getById(int id) {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.id = :id", Genre.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = em.createQuery("SELECT g FROM Genre g", Genre.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void delete(int id) {
        Query query = em.createQuery("DELETE from Genre where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
