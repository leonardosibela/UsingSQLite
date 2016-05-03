package com.sibela.examples.usingsql.repository;

import com.sibela.examples.usingsql.model.Book;

import java.util.List;

public interface BookDao {

    void open();

    void close();

    List<Book> getAllBooks();

    Book getBookById(long id);

    long insert(Book book);

    int remove(long id);

    int update(Book book);
}
