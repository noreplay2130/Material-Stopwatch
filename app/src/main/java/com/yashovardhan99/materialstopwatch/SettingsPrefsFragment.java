package com.yashovardhan99.materialstopwatch;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

/**
 * Created by Yashovardhan99 on 3/1/19 as a part of Stopwatch.
 */
public class SettingsPrefsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.setting_prefs, rootKey);
    }
}