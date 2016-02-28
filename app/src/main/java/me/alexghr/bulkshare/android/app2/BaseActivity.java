package me.alexghr.bulkshare.android.app2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String prefThemeKey = getString(R.string.pref_theme_key);
        String prefDarkTheme = getString(R.string.pref_theme_holo_dark_value);
        String prefDefaultTheme = getString(R.string.pref_theme_default_value);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = settings.getString(prefThemeKey, prefDefaultTheme);

        if (theme.equals(prefDarkTheme)) {
            setTheme(R.style.BulkShare_Dark);
        } else {
            setTheme(R.style.BulkShare_Light);
        }
    }
}
