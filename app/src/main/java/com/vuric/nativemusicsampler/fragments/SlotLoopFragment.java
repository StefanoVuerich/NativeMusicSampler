package com.vuric.nativemusicsampler.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vuric.nativemusicsampler.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SlotLoopFragment extends Fragment {

    public static final String _TAG = SlotLoopFragment.class.getSimpleName();

    public static Fragment newInstance() {

        Bundle args = new Bundle();

        Fragment fragment = new SlotLoopFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.slot_loop_fragment_layout, container, false);
    }

}
