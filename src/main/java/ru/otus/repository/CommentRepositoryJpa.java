package ru.otus.repository;

import org.springframework.stereotype.Repository;
import ru.otus.entity.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    public CommentRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public void create(Comment comment) {
        em.persist(comment);
    }

    @Override
    public List<Comment> getByBookId(int id) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c where book_id = :id", Comment.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void delete(long id) {
        Query query = em.createQuery("DELETE from Comment where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

}
