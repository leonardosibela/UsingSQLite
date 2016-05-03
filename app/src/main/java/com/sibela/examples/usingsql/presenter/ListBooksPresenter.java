package com.sibela.examples.usingsql.presenter;

import com.sibela.examples.usingsql.model.Book;

import java.util.List;

public interface ListBooksPresenter {

    List<Book> getAllBooks();

    void saveBook(Book book);

}
