package com.example.indrianinatalia.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by indrianinatalia on 2/9/17.
 */

public class SQLHelperQ extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "dbAkil";
    private static final String TABLE_NAME = "tbAkil";
    private static final int DATABASE_VERSION = 1;

    private static final String id = "id";
    private static final String name = "name";
    private static final String gen = "gen";
    private static final String numb = "numb";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + id + " integer primary key,"
            + name + " varchar(225)," + gen + " varchar(225)," + numb + " varchar(225))";

    public SQLHelperQ(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
