package com.sibela.examples.usingsql.presenter;

import com.sibela.examples.usingsql.ListBooksActivity;
import com.sibela.examples.usingsql.dao.BookDaoSqlite;
import com.sibela.examples.usingsql.model.Book;
import com.sibela.examples.usingsql.repository.BookDao;

import java.util.List;

public class ListBooksPresenterImpl implements ListBooksPresenter {

    private ListBooksActivity mActivity;
    BookDao bookDao;

    public ListBooksPresenterImpl(ListBooksActivity activity) {
        mActivity = activity;
        bookDao = new BookDaoSqlite(activity);
    }

    @Override
    public List<Book> getAllBooks() {
        bookDao.open();
        List<Book> allBooks = bookDao.getAllBooks();
        bookDao.close();
        return allBooks;
    }

    @Override
    public void saveBook(Book book) {

        bookDao.open();
        long row = bookDao.insert(book);
        bookDao.close();

        if (row == - 1) {
            mActivity.setBookNotSavedMessage();
        } else {
            mActivity.setContent(book);
        }
    }
}
