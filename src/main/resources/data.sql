INSERT INTO AUTHORS (ID, NAME, LASTNAME) VALUES (1, 'Дуглас', 'Адамс');
INSERT INTO AUTHORS (ID, NAME, LASTNAME) VALUES (2, 'Джон', 'Толкин');
INSERT INTO AUTHORS (ID, NAME, LASTNAME) VALUES (3, 'Stiven', 'King');

INSERT INTO GENRES (ID, NAME) VALUES (1, 'фантастика');
INSERT INTO GENRES (ID, NAME) VALUES (2, 'фэнтези');
INSERT INTO GENRES (ID, NAME) VALUES (3, 'стихи');

INSERT INTO BOOKS (ID, NAME, SUMMARY, AUTHOR_ID, GENRE_ID) VALUES (1, 'Автостопом по галактике', 'Путеводитель для путешествующих по галактике автостопом', 1, 1);
INSERT INTO BOOKS (ID, NAME, SUMMARY, AUTHOR_ID, GENRE_ID) VALUES (2, 'Властелин Колец', 'Одно из самых известных произведений жанра фэнтези', 2, 2);