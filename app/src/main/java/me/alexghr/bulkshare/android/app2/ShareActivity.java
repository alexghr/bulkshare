package me.alexghr.bulkshare.android.app2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import me.alexghr.bulkshare.android.app2.db.DBAccess;
import me.alexghr.bulkshare.android.app2.db.Link;

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
        long id = dbAccess.insertNewLink(link);

        if (id > -1) {
            Toast.makeText(this, R.string.toast_link_added, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.toast_link_duplicate, Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}
