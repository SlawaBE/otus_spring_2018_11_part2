package ru.otus.bee;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.DomainObjectPermission;
import org.springframework.security.acls.domain.MongoAcl;
import org.springframework.security.acls.domain.MongoSid;
import ru.otus.entity.Book;
import ru.otus.entity.Role;
import ru.otus.entity.User;

import java.util.*;

import static java.util.Collections.singletonList;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "addBooks", author = "SlawaBE")
    public void insertBooks(DB db) {
        DBCollection booksDbCollection = db.getCollection("books");
        List<BasicDBObject> books = new ArrayList<>();
        books.add(new BasicDBObject()
                .append("name", "Автостопом по галактике")
                .append("summary", "Путеводитель для путешествующих по галактике автостопом")
                .append("authors", singletonList("Дуглас Адамс"))
                .append("genres", singletonList("фантастика")));
        books.add(new BasicDBObject()
                .append("name", "Властелин Колец")
                .append("summary", "Одно из самых известных произведений жанра фэнтези")
                .append("authors", singletonList("Джон Толкин"))
                .append("genres", Arrays.asList("приключения", "фэнтези")));

        booksDbCollection.insert(books);
    }

    @ChangeSet(order = "002", id = "addComments", author = "SlawaBE")
    public void insertComments(DB db) {
        DBCollection booksCollection = db.getCollection("books");

        Object bookId = null;
        for (DBObject obj : booksCollection.find()) {
            if ("Автостопом по галактике".equals(obj.get("name"))) {
                bookId = obj.get("_id");
                break;
            }
        }

        DBCollection commentsCollection = db.getCollection("comments");
        List<BasicDBObject> comments = new ArrayList<>();
        comments.add(new BasicDBObject()
                .append("userName", "test")
                .append("text", "Главный вопрос жизни, Вселенной и всего такого?")
                .append("sendDate", new Date())
                .append("book", new DBRef("books", bookId))
        );
        comments.add(new BasicDBObject()
                .append("userName", "Deep Thought")
                .append("text", "42")
                .append("sendDate", new Date())
                .append("book", new DBRef("books", bookId))
        );

        commentsCollection.insert(comments);
    }

    @ChangeSet(order = "003", id = "addUsers", author = "SlawaBE")
    public void insertUsers(MongoTemplate mongoTemplate) {
        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");
        mongoTemplate.insert(adminRole);
        mongoTemplate.insert(userRole);

        User admin = new User("admin", "$2a$10$BoQrql.p4u1UKMqELmywZOSwgbu7eCZGdmzLMVMopHIkYShkMKEFO");
        admin.setRoles(singletonList(adminRole));

        User user = new User("user", "$2a$10$f52p8IkiwusqPKmuc53G2.rnxvrczXhFkXvfcq7ZMnxGi5cM4zCPi");
        user.setRoles(singletonList(userRole));

        mongoTemplate.insert(admin);
        mongoTemplate.insert(user);
    }

    @ChangeSet(order = "004", id = "addAcls", author = "SlawaBE")
    public void insertAcls(MongoTemplate mongoTemplate) {
        List<Book> books = mongoTemplate.find(new Query(), Book.class);

        for (Book book : books) {
            MongoSid admin = new MongoSid("admin");
            MongoAcl acl = new MongoAcl(book.getId(), Book.class.getName(), UUID.randomUUID().toString(), admin, null, true);
            acl.getPermissions().add(new DomainObjectPermission(UUID.randomUUID().toString(), admin, BasePermission.ADMINISTRATION.getMask(), true, false, false));
            mongoTemplate.insert(acl);
        }

    }

}