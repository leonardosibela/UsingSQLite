package com.sibela.examples.usingsql.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sibela.examples.usingsql.model.Book;
import com.sibela.examples.usingsql.repository.BookDao;

import java.util.ArrayList;
import java.util.List;

public class BookDaoSqlite implements BookDao {

    private SQLiteDatabase sqLiteDatabase;
    private DatabaseManager dbManager;

    public static final String TABLE = "books";

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String AUTHOR = "author";
    public static final String EDITION = "edition";
    public static final String PUBLICATION_YEAR = "publication_year";

    public static final String[] ALL_COLUMNS = {
            ID, TITLE, DESCRIPTION, AUTHOR, EDITION, PUBLICATION_YEAR
    };

    public BookDaoSqlite(Context context) {
        dbManager = new DatabaseManager(context);
    }

    @Override
    public void open() throws android.database.SQLException {
        sqLiteDatabase = dbManager.getWritableDatabase();
    }

    @Override
    public void close() {
        dbManager.close();
    }

    @Nullable
    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(TABLE, ALL_COLUMNS, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Book book = cursorToBook(cursor);
            books.add(book);
            cursor.moveToNext();
        }

        cursor.close();
        return books;
    }

    @Override
    public Book getBookById(long id) {
        Cursor cursor = sqLiteDatabase.query(TABLE, ALL_COLUMNS, orderByIdClause(), new String[]{String.valueOf(id)}, null, null, null);
        cursor.moveToFirst();
        return cursorToBook(cursor);
    }

    @Override
    public long insert(Book book) {
        ContentValues values = bookToContentValues(book);
        return sqLiteDatabase.insert(TABLE, null, values);
    }

    @Override
    public int remove(long id) {
        return sqLiteDatabase.delete(TABLE, orderByIdClause(), new String[]{String.valueOf(id)});
    }

    @NonNull
    private String orderByIdClause() {
        return ID + " = ? ";
    }

    @Override
        public int update(Book book) {
            ContentValues values = bookToContentValues(book);
            return sqLiteDatabase.update(TABLE, values, orderByIdClause(), new String[]{String.valueOf(book.getId())});
        }

    private ContentValues bookToContentValues(Book book) {

        ContentValues values = new ContentValues();

        values.put(TITLE, book.getTitle());
        values.put(DESCRIPTION, book.getDescription());
        values.put(AUTHOR, book.getAuthor());
        values.put(EDITION, book.getEdition());
        values.put(PUBLICATION_YEAR, book.getPublicationYear());

        return values;
    }

    private Book cursorToBook(Cursor cursor) {
        Book book = new Book();

        book.setId(cursor.getInt(0));
        book.setTitle(cursor.getString(1));
        book.setDescription(cursor.getString(2));
        book.setAuthor(cursor.getString(3));
        book.setEdition(cursor.getInt(4));
        book.setPublicationYear(cursor.getInt(5));

        return book;
    }
}
