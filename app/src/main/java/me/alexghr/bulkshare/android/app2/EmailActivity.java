package me.alexghr.bulkshare.android.app2;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class EmailActivity extends BaseActivity {

    public static final int MENU_ITEM_ID_SETTINGS = 0x39afd;
    private Fragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.email_activity);

        Bundle fragmentArgs = new Bundle();
        fragmentArgs.putInt(LinksFragment.ARGUMENT_LIST_ID, 1);
        fragment = Fragment.instantiate(this, LinksFragment.class.getName(), fragmentArgs);

        getFragmentManager().beginTransaction().replace(R.id.flListContainer, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuItem settings = menu.add(Menu.NONE, MENU_ITEM_ID_SETTINGS, Menu.NONE, "Settings");

        settings.setIntent(new Intent(this, SettingsActivity.class));
        settings.setIcon(android.R.drawable.ic_menu_preferences);
        settings.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
