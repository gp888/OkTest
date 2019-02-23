package com.gp.oktest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBService extends SQLiteOpenHelper {
    private String CREATE_BOOK = "create table book(bookId integer primarykey,bookName text);";
    private String CREATE_TEMP_BOOK = "alter table book rename to _temp_book";
    private String INSERT_DATA = "insert into book select *,'' from _temp_book";
    private String DROP_BOOK = "drop table _temp_book";

    public DBService(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (newVersion) {
            case 2:
                db.beginTransaction();
                db.execSQL(CREATE_TEMP_BOOK);
                db.execSQL(CREATE_BOOK);
                db.execSQL(INSERT_DATA);
                db.execSQL(DROP_BOOK);
                db.setTransactionSuccessful();
                db.endTransaction();
                break;
        }
    }
}