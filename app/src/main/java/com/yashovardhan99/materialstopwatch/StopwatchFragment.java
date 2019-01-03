package com.yashovardhan99.materialstopwatch;

import android.os.Bundle;

import com.yashovardhan99.timeit.Stopwatch;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by Yashovardhan99 on 2/1/19 as a part of Stopwatch.
 */
public class StopwatchFragment extends Fragment {

    Stopwatch stopwatch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        stopwatch = new Stopwatch();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        stopwatch.setTextView(Objects.requireNonNull(getActivity()).findViewById(R.id.elapsed));
    }
}
