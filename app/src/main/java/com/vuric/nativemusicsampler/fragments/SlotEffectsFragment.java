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
public class SlotEffectsFragment extends Fragment {

    public static final String _TAG = SlotEffectsFragment.class.getSimpleName();

    public static Fragment newInstance() {
        
        Bundle args = new Bundle();

        Fragment fragment = new SlotEffectsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.slot_effects_fragment_layout, container, false);
    }

}
