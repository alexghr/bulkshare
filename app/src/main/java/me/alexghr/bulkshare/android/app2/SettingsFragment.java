package me.alexghr.bulkshare.android.app2;


import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        PreferenceManager.setDefaultValues(getActivity(), R.xml.settings, false);
    }
}
