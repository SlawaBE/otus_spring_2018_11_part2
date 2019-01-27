INSERT INTO AUTHOR (ID, NAME, LASTNAME) VALUES (1, 'Дуглас', 'Адамс');
INSERT INTO AUTHOR (ID, NAME, LASTNAME) VALUES (2, 'Джон', 'Толкин');
INSERT INTO AUTHOR (ID, NAME, LASTNAME) VALUES (3, 'Stiven', 'King');

INSERT INTO GENRE (ID, NAME) VALUES (1, 'фантастика');
INSERT INTO GENRE (ID, NAME) VALUES (2, 'фэнтези');
INSERT INTO GENRE (ID, NAME) VALUES (3, 'стихи');

INSERT INTO BOOK (ID, NAME, SUMMARY, AUTHOR_ID, GENRE_ID) VALUES (1, 'Автостопом по галактике', 'Путеводитель для путешествующих по галактике автостопом', 1, 1);
INSERT INTO BOOK (ID, NAME, SUMMARY, AUTHOR_ID, GENRE_ID) VALUES (2, 'Властелин Колец', 'Одно из самых известных произведений жанра фэнтези', 2, 2);

INSERT INTO COMMENT (SEND_DATE, TEXT, USERNAME, BOOK_ID) VALUES ('2019-01-22 15:34:08', 'Главный вопрос жизни, Вселенной и всего такого?', 'test', 1);
INSERT INTO COMMENT (SEND_DATE, TEXT, USERNAME, BOOK_ID) VALUES ('2019-01-22 15:34:15.5', '42', 'Deep Thought', 1);
