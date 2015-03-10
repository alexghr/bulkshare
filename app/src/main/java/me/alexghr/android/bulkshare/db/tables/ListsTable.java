package me.alexghr.android.bulkshare.db.tables;

public class ListsTable {

    public static final String TABLE_NAME = "lists";
    public static final String SQL_DROP = "drop table " + TABLE_NAME;
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String SQL_CREATE = "create table " + TABLE_NAME + "("
            + COLUMN_ID + " integer primary key, "
            + COLUMN_NAME + " text not null)";

}
