package com.sibela.examples.usingsql.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String BASE_NAME = "books.db";
    private static final int BASE_VERIONS = 1;

    public DatabaseManager(Context context) {
        super(context, BASE_NAME, null, BASE_VERIONS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String tabelaProdutos =
                "CREATE TABLE books (" +
                        "id TEXT PRIMARY KEY, " +
                        "title TEXT NOT NULL, " +
                        "description TEXT NOT NULL, " +
                        "author TEXT, " +
                        "edition INTEGER, " +
                        "publication_year INTEGER)";

        db.execSQL(tabelaProdutos);

        db.execSQL("insert into books values (1, 'Title 1', 'Description 1', 'Author 1', 3, 2008);");
        db.execSQL("insert into books values (2, 'Title 2', 'Description 2', 'Author 2', 2, 2012);");
        db.execSQL("insert into books values (3, 'Title 3', 'Description 3', 'Author 3', 2, 2010);");
        db.execSQL("insert into books values (4, 'Title 4', 'Description 4', 'Author 1', 1, 2008);");
        db.execSQL("insert into books values (5, 'Title 5', 'Description 5', 'Author 2', 2, 2003);");
        db.execSQL("insert into books values (6, 'Title 6', 'Description 6', 'Author 3', 2, 2000);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}