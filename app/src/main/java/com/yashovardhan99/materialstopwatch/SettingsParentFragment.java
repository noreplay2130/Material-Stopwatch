package com.yashovardhan99.materialstopwatch;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

/**
 * Created by Yashovardhan99 on 4/1/19 as a part of Stopwatch.
 */
public class SettingsParentFragment extends Fragment {

    private final String TAG = "SettingsParentFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Started");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: started");

        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        ((MainActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        SettingsPrefsFragment settingsPrefsFragment = new SettingsPrefsFragment();
        getChildFragmentManager().beginTransaction()
                .replace(R.id.settings_holder, settingsPrefsFragment).commit();
        return rootView;
    }
}
