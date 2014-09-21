package com.github.alexghr.bulkshare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.github.alexghr.bulkshare.db.DBAccess;
import com.github.alexghr.bulkshare.db.Link;

public class ShareActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();

        Link link = new Link();
        link.setLink(extras.getString(Intent.EXTRA_TEXT));
        if (extras.containsKey(Intent.EXTRA_TITLE)) {
            link.setTitle(extras.getString(Intent.EXTRA_TITLE));
        } else {
            link.setTitle(extras.getString(Intent.EXTRA_SUBJECT));
        }

        DBAccess dbAccess = new DBAccess(this);
        dbAccess.insertNewLink(link);

        Toast.makeText(this, "Got something", Toast.LENGTH_SHORT).show();
        finish();
    }
}