package ru.otus.bee;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.*;

import java.util.*;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "addBooks", author = "SlawaBE")
    public void insertBooks(DB db) {
        DBCollection booksDbCollection = db.getCollection("books");
        List<BasicDBObject> books = new ArrayList<>();
        books.add(new BasicDBObject()
                .append("name", "Автостопом по галактике")
                .append("summary", "Путеводитель для путешествующих по галактике автостопом")
                .append("authors", Collections.singletonList("Дуглас Адамс"))
                .append("genres", Collections.singletonList("фантастика")));
        books.add(new BasicDBObject()
                .append("name", "Властелин Колец")
                .append("summary", "Одно из самых известных произведений жанра фэнтези")
                .append("authors", Collections.singletonList("Джон Толкин"))
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

}