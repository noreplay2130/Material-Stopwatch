package com.yashovardhan99.materialstopwatch;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    @SuppressWarnings("FieldCanBeLocal")
    private final String FRAG_KEY = "STOPWATCH_FRAG";
    private final String TAG = "MainFragment";
    private final String TIME_TEXT_KEY = "STOPWATCH_TIME";
    private StopwatchFragment stopwatchFragment;
    private TextView elapsedTime;
    private ImageButton resetButton;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();

        stopwatchFragment = (StopwatchFragment) fragmentManager.findFragmentByTag(FRAG_KEY);
        if (stopwatchFragment == null) {
            stopwatchFragment = new StopwatchFragment();
            fragmentManager.beginTransaction().add(stopwatchFragment, FRAG_KEY).commit();
            Log.d(TAG, "onCreate: Created new StopwatchFragment");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        elapsedTime = rootView.findViewById(R.id.elapsed);
        resetButton = rootView.findViewById(R.id.reset);
        elapsedTime.setOnClickListener(this::onClick);
        resetButton.setOnClickListener(this::onClick);

        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        ((MainActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        if (savedInstanceState != null && savedInstanceState.containsKey(TIME_TEXT_KEY) && stopwatchFragment.stopwatch.isPaused()) {
            elapsedTime.setText(savedInstanceState.getString(TIME_TEXT_KEY));
            resetButton.setVisibility(View.VISIBLE);
            Log.d(TAG, "onCreate: Restored state");
        } else
            resetButton.setVisibility(View.GONE);

        return rootView;
    }

    public void onClick(View view) {
        Log.d(TAG, "onClick: Clicked = " + view);
        switch (view.getId()) {
            case R.id.elapsed:
                if (stopwatchFragment.stopwatch.isStarted()) {
                    if (stopwatchFragment.stopwatch.isPaused()) {
                        stopwatchFragment.stopwatch.resume();
                        resetButton.setVisibility(View.GONE);
                    } else {
                        stopwatchFragment.stopwatch.pause();
                        resetButton.setVisibility(View.VISIBLE);
                    }
                } else
                    stopwatchFragment.stopwatch.start();
                break;

            case R.id.reset:
                stopwatchFragment.stopwatch.stop();
                elapsedTime.setText(R.string.default_time);
                resetButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Log.d(TAG, "onOptionsItemSelected: Settings");
                SettingsPrefsFragment prefsFragment = new SettingsPrefsFragment();
                Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                        .beginTransaction().replace(R.id.main_layout, prefsFragment).commit();
                return true;
            case R.id.about:
                Log.d(TAG, "onOptionsItemSelected: About");
                return true;
            default:
                Log.d(TAG, "onOptionsItemSelected: Defaut - " + item.getTitle().toString());
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: Saving text");
        outState.putString(TIME_TEXT_KEY, elapsedTime.getText().toString());
        super.onSaveInstanceState(outState);
    }
}
