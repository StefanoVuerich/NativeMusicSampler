package com.vuric.nativemusicsampler.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vuric.nativemusicsampler.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SlotInfoFragment extends Fragment {

    public static final String _TAG = SlotInfoFragment.class.getSimpleName();

    public static Fragment newInstance() {

        Bundle args = new Bundle();

        Fragment fragment = new SlotInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.slot_info_fragment_layout, container, false);
    }
}
