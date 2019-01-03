package com.yashovardhan99.materialstopwatch;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    @SuppressWarnings("FieldCanBeLocal")
    private final String FRAG_KEY = "STOPWATCH_FRAG";
    private final String TAG = "MainActivity";
    private final String TIME_TEXT_KEY = "STOPWATCH_TIME";
    StopwatchFragment stopwatchFragment;
    private TextView elapsedTime;
    private ImageButton resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();

        stopwatchFragment = (StopwatchFragment) fragmentManager.findFragmentByTag(FRAG_KEY);

        if (stopwatchFragment == null) {
            stopwatchFragment = new StopwatchFragment();
            fragmentManager.beginTransaction().add(stopwatchFragment, FRAG_KEY).commit();
            Log.d(TAG, "onCreate: Created new StopwatchFragment");
        }
        elapsedTime = findViewById(R.id.elapsed);
        elapsedTime.setOnClickListener(this::onClick);
        resetButton = findViewById(R.id.reset);
        resetButton.setOnClickListener(this::onClick);

        if (savedInstanceState != null && savedInstanceState.containsKey(TIME_TEXT_KEY) && stopwatchFragment.stopwatch.isPaused()) {
            elapsedTime.setText(savedInstanceState.getString(TIME_TEXT_KEY));
            resetButton.setVisibility(View.VISIBLE);
            Log.d(TAG, "onCreate: Restored state");
        } else
            resetButton.setVisibility(View.GONE);

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
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: Saving text");
        outState.putString(TIME_TEXT_KEY, elapsedTime.getText().toString());
        super.onSaveInstanceState(outState);
    }
}
