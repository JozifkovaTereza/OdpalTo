package com.example.appkaodpalto;

import java.util.Map;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.example.appkaodpalto.ActivityHelper;

public class PreferencesActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHelper.initialize(this);
    //Android 2.2
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference pref = findPreference(key);

        if (pref instanceof ListPreference) {
            ListPreference listPref = (ListPreference) pref;
            pref.setSummary(listPref.getEntry());
            ActivityHelper.initialize(this);
        }

        if (pref instanceof EditTextPreference) {
            EditTextPreference editPref = (EditTextPreference) pref;
            pref.setSummary(editPref.getText());
        }
    }

    @Override
    protected void onPause() {

        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
        Map<String, ?> keys = PreferenceManager.getDefaultSharedPreferences(this).getAll();
        //https://www.baeldung.com/java-map-entry
        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            Preference pref = findPreference(entry.getKey());
            if (pref != null) {
                pref.setSummary(entry.getValue().toString());
            }
        }

        super.onResume();
    }

}

