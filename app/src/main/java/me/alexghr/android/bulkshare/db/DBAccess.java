package me.alexghr.android.bulkshare.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import me.alexghr.android.bulkshare.db.tables.LinksTable;

import java.util.ArrayList;
import java.util.List;

public class DBAccess {

    private final DBOpener opener;

    public DBAccess(Context context) {
        opener = new DBOpener(context);
    }

    public long insertNewLink(Link link) {
        final SQLiteDatabase db = opener.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(LinksTable.COLUMN_LINK, link.getLink());

            String title = link.getTitle();
            if (title != null) {
                values.put(LinksTable.COLUMN_TITLE, title);
            }

            values.put(LinksTable.COLUMN_LIST_ID, link.getListId());

            long id = db.insertOrThrow(LinksTable.TABLE_NAME, null, values);
            link.setId(id);

            return id;
        } finally {
            db.close();
        }
    }

    private List<Link> getLinks(SQLiteDatabase db, String query, String... args) {
        final Cursor cursor = db.rawQuery(query, args);

        try {
            List<Link> links = new ArrayList<Link>();
            if (cursor.moveToFirst()) {
                int linkColumnIndex = cursor.getColumnIndex(LinksTable.COLUMN_LINK);
                int idColumnIndex = cursor.getColumnIndex(LinksTable.COLUMN_ID);
                int titleColumnIndex = cursor.getColumnIndex(LinksTable.COLUMN_TITLE);
                int listIdColumnIndex = cursor.getColumnIndex(LinksTable.COLUMN_LIST_ID);

                do {
                    links.add(new Link(cursor.getString(titleColumnIndex),
                            cursor.getString(linkColumnIndex),
                            cursor.getLong(idColumnIndex),
                            cursor.getLong(listIdColumnIndex)));
                } while (cursor.moveToNext());
            }

            return links;
        } finally {
            cursor.close();
        }
    }

    public List<Link> getLinks(int listId, boolean sent) {
        final SQLiteDatabase db = opener.getReadableDatabase();

        try {
            return getLinks(db, "select * from " + LinksTable.TABLE_NAME + " where " +
                            LinksTable.COLUMN_LIST_ID + "=? and " +
                            LinksTable.COLUMN_SENT + "=?",
                    String.valueOf(listId), String.valueOf(sent ? 1 : 0));
        } finally {
            db.close();
        }
    }

    public boolean setSentStatus(long linkId, boolean sent) {
        final SQLiteDatabase db = opener.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(LinksTable.COLUMN_SENT, sent ? 1 : 0);

            return db.update(LinksTable.TABLE_NAME, values, LinksTable.COLUMN_ID + "=?",
                    new String[]{String.valueOf(linkId)}) != 0;
        } finally {
            db.close();
        }
    }

    public List<Link> getLinks() {
        final SQLiteDatabase db = opener.getReadableDatabase();

        try {
            return getLinks(db, "select * from " + LinksTable.TABLE_NAME);
        } finally {
            db.close();
        }
    }

    public boolean deleteLink(Link link) {
        final SQLiteDatabase db = opener.getWritableDatabase();

        try {
            return db.delete(LinksTable.TABLE_NAME, LinksTable.COLUMN_ID + "=?",
                    new String[]{String.valueOf(link.getId())}) != 0;
        } finally {
            db.close();
        }
    }

}
