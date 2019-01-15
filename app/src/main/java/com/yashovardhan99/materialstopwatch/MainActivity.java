package com.yashovardhan99.materialstopwatch;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {

    public static final String FRAG_KEY = "MAIN_FRAG_KEY";
    static StopwatchFragment stopwatchFragment;
    @SuppressWarnings("FieldCanBeLocal")
    private final String TAG = "MainActivity";
    private final String FRAG_TAG = "MAIN_FRAG_TAG";
    @SuppressWarnings("FieldCanBeLocal")
    private final String STOPWATCH_FRAG_TAG = "STOPWATCH_FRAG_TAG";
    Fragment mainFragment;
    FragmentManager fragmentManager;
    NavController controller;

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

        controller = Navigation.findNavController(this, R.id.main_layout);
//
//        if (savedInstanceState != null && savedInstanceState.containsKey(FRAG_KEY)) {
//            mainFragment = fragmentManager.getFragment(savedInstanceState, FRAG_KEY);
//            Log.d(TAG, "onCreate: Restoring MainFragment");
//        }
//        if (mainFragment == null) {
//            Log.d(TAG, "onCreate: new MainFragment");
//            mainFragment = new MainFragment();
//        }
//        fragmentManager.beginTransaction().replace(R.id.main_layout, mainFragment, FRAG_TAG).commit();
//        Log.d(TAG, "onCreate: Transaction done");
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateSpeed();
    }

    void startSettings() {
        mainFragment = new SettingsParentFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.main_layout, mainFragment, FRAG_TAG)
                .addToBackStack(null).commit();
    }

    void updateSpeed() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String speedString = sharedPreferences.getString(getString(R.string.speed_key), "100");
        updateSpeed(speedString);
    }

    /*@Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        mainFragment = fragmentManager.findFragmentById(R.id.main_layout);
        Log.d(TAG, "onSaveInstanceState: MainFragment : " + mainFragment);
        if (mainFragment != null && mainFragment.isAdded())
            fragmentManager.putFragment(outState, FRAG_KEY, mainFragment);
        super.onSaveInstanceState(outState);
    }*/

    public boolean updateSpeed(Object newSpeed) {
        try {
            assert newSpeed != null;
            int speed = Integer.parseInt(newSpeed.toString());
            stopwatchFragment.stopwatch.setClockDelay(speed);
            return true;
        } catch (NumberFormatException e) {
            Log.e(TAG, "updateSpeed: NumberFormatException", e);
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: " + item);
        switch (item.getItemId()) {
            case android.R.id.home:
//                getSupportFragmentManager().popBackStack();
                return controller.navigateUp();
            case R.id.settings:
                Log.d(TAG, "onOptionsItemSelected: Settings");
//                startSettings();
                controller.navigate(R.id.action_mainFragment_to_settingsParentFragment);
                return true;
            case R.id.about:
                Log.d(TAG, "onOptionsItemSelected: About");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
