package com.yashovardhan99.materialstopwatch;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    public static final String FRAG_KEY = "MAIN_FRAG_KEY";
    @SuppressWarnings("FieldCanBeLocal")
    private final String TAG = "MainActivity";
    private final String FRAG_TAG = "MAIN_FRAG_TAG";
    @SuppressWarnings("FieldCanBeLocal")
    private final String STOPWATCH_FRAG_TAG = "STOPWATCH_FRAG_TAG";
    Fragment mainFragment;
    static StopwatchFragment stopwatchFragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        stopwatchFragment = (StopwatchFragment) fragmentManager.findFragmentByTag(STOPWATCH_FRAG_TAG);
        if (stopwatchFragment == null) {
            stopwatchFragment = new StopwatchFragment();
            fragmentManager.beginTransaction().add(stopwatchFragment, STOPWATCH_FRAG_TAG).commit();
            Log.d(TAG, "onCreate: Created new StopwatchFragment");
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(FRAG_KEY)) {
            mainFragment = fragmentManager.getFragment(savedInstanceState, FRAG_KEY);
            Log.d(TAG, "onCreate: Restoring MainFragment");
        }
        if (mainFragment == null) {
            Log.d(TAG, "onCreate: new MainFragment");
            mainFragment = new MainFragment();
        }
        fragmentManager.beginTransaction().replace(R.id.main_layout, mainFragment, FRAG_TAG).commit();
        Log.d(TAG, "onCreate: Transaction done");
    }

    void startSettings() {
        mainFragment = new SettingsParentFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.main_layout, mainFragment, FRAG_TAG)
                .addToBackStack(null).commit();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        mainFragment = fragmentManager.findFragmentById(R.id.main_layout);
        Log.d(TAG, "onSaveInstanceState: MainFragment : " + mainFragment);
        if (mainFragment != null && mainFragment.isAdded())
            fragmentManager.putFragment(outState, FRAG_KEY, mainFragment);
        super.onSaveInstanceState(outState);
    }
}
