package com.yashovardhan99.materialstopwatch;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @SuppressWarnings("FieldCanBeLocal")
    private final String TAG = "MainActivity";
    MainFragment mainFragment;
    private final String FRAG_KEY = "MAIN_FRAG_KEY";
    private final String FRAG_TAG = "MAIN_FRAG_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null && savedInstanceState.containsKey(FRAG_KEY)) {
            mainFragment = (MainFragment) getSupportFragmentManager().getFragment(savedInstanceState, FRAG_KEY);
            Log.d(TAG, "onCreate: Restoring MainFragment");
        }
        if (mainFragment == null) {
            Log.d(TAG, "onCreate: new MainFragment");
            mainFragment = new MainFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, mainFragment, FRAG_TAG).commit();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        getSupportFragmentManager().putFragment(outState, FRAG_KEY, mainFragment);
        super.onSaveInstanceState(outState);
    }
}
