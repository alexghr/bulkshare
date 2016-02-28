package me.alexghr.bulkshare.android.app2.db.tables;

public class LinksTable {

    public static final String TABLE_NAME = "links";
    public static final String SQL_DROP = "drop table " + TABLE_NAME;
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LINK = "link";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_SENT = "sent";
    public static final String COLUMN_LIST_ID = "list_id";

    public static final String SQL_CREATE_V2 = "create table " + TABLE_NAME + "("
            + COLUMN_ID + " integer primary key, "
            + COLUMN_LINK + " text not null, "
            + COLUMN_TITLE + " text not null default '', "
            + COLUMN_SENT + " integer default 0, "
            + COLUMN_LIST_ID + " integer default 1)";

    public static final String SQL_CREATE_V1 = "create table " + TABLE_NAME + "("
            + COLUMN_ID + " integer primary key, "
            + COLUMN_LINK + " text not null, "
            + COLUMN_TITLE + " text not null default '', "
            + COLUMN_SENT + " integer default 0, "
            + COLUMN_LIST_ID + " integer default 1)";

}
