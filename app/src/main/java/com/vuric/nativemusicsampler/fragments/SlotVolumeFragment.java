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
public class SlotVolumeFragment extends Fragment {

    public static final String _TAG = SlotVolumeFragment.class.getSimpleName();

    public static Fragment newInstance() {

        Bundle args = new Bundle();

        Fragment fragment = new SlotVolumeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.slot_volume_fragment_layout, container, false);
    }

}
