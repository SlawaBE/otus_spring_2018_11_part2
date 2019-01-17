package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.dao.BookDao;
import ru.otus.model.Book;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public int create(Book book) {
        return bookDao.create(book);
    }

    @Override
    public void update(Book book) {
        Book oldBook = bookDao.getById(book.getId());
        oldBook.setName(book.getName().isEmpty() ? oldBook.getName() : book.getName());
        oldBook.setSummary(book.getSummary().isEmpty() ? oldBook.getSummary() : book.getSummary());
        oldBook.setAuthor(book.getAuthor() == null ? oldBook.getAuthor() : book.getAuthor());
        oldBook.setGenre(book.getGenre() == null ? oldBook.getGenre() : book.getGenre());
        bookDao.update(oldBook);
    }

    @Override
    public Book getById(int id) {
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public void delete(int id) {
        bookDao.delete(id);
    }

}
