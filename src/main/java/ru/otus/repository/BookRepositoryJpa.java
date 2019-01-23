package ru.otus.repository;

import org.springframework.stereotype.Repository;
import ru.otus.entity.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;


@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    public BookRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public int create(Book book) {
        em.persist(book);
        return book.getId();
    }

    @Override
    @Transactional
    public void update(Book book) {
        em.merge(book);
    }

    @Override
    public Book getById(int id) {
        TypedQuery<Book> query = em.createQuery("select b from Book b where id = :id", Book.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void delete(int id) {
        Query query = em.createQuery("delete from Book where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
