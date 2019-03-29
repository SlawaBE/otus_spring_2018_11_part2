package ru.otus.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.DomainObjectPermission;
import org.springframework.security.acls.domain.MongoAcl;
import org.springframework.security.acls.domain.MongoSid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.entity.Book;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;
import ru.otus.repository.ReactiveAclRepository;

import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    private final CommentRepository commentRepository;

    private final ReactiveAclRepository aclRepository;

    public BookServiceImpl(BookRepository repository, CommentRepository commentRepository, ReactiveAclRepository aclRepository) {
        this.repository = repository;
        this.commentRepository = commentRepository;
        this.aclRepository = aclRepository;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Mono<Book> update(Book book, UserDetails userDetails) {
        return repository.save(book).doOnSuccess((savedBook) -> {
            aclRepository.findByInstanceIdAndClassName(savedBook.getId(), Book.class.getName())
                    .switchIfEmpty(aclRepository.save(createMongoAcl(savedBook, userDetails))).subscribe();
        });
    }

    private MongoAcl createMongoAcl(Book book, UserDetails userDetails) {
        MongoSid user = new MongoSid(userDetails.getUsername());
        MongoAcl acl = new MongoAcl(book.getId(), Book.class.getName(), UUID.randomUUID().toString(), user, null, true);
        acl.getPermissions().add(new DomainObjectPermission(UUID.randomUUID().toString(), user, BasePermission.ADMINISTRATION.getMask(), true, false, false));
        return acl;
    }

    @Override
    public Mono<Book> getById(String id) {
        return repository.findById(id);
    }

    @Override
    public Flux<Book> getAll() {
        return repository.findAll();
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'ru.otus.entity.Book', admin)")
    public Mono<Void> delete(String id) {
        return repository.deleteById(id).and(commentRepository.deleteByBookId(id)).doOnSuccess((v) -> aclRepository.deleteByInstanceId(id).subscribe());
    }

    @Override
    public Flux<Book> findByAuthor(String name) {
        return repository.findByAuthor(name);
    }

    @Override
    public Flux<Book> findByGenre(String genre) {
        return repository.findByGenresContaining(genre);
    }

}
