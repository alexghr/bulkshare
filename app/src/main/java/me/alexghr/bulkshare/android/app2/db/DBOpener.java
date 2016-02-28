package me.alexghr.bulkshare.android.app2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import me.alexghr.bulkshare.android.app2.db.tables.LinksTable;
import me.alexghr.bulkshare.android.app2.db.tables.ListsTable;

public class DBOpener extends SQLiteOpenHelper {

    public static final String DB_NAME = "bulkshare";
    public static final int DB_VER = 1;

    public DBOpener(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ListsTable.SQL_CREATE);
        db.execSQL(LinksTable.SQL_CREATE);

        ContentValues values = new ContentValues();
        values.put(ListsTable.COLUMN_NAME, "Default");

        db.insertOrThrow(ListsTable.TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*db.execSQL(LinksTable.SQL_DROP);
        db.execSQL(ListsTable.SQL_DROP);
        onCreate(db);*/
    }
}
