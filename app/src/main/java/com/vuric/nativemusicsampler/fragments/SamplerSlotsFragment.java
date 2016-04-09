package com.vuric.nativemusicsampler.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.vuric.nativemusicsampler.R;
import com.vuric.nativemusicsampler.adapters.SanplerSlotsAdapter;
import com.vuric.nativemusicsampler.utils.Constants;

/**
 * Created by stefano on 4/5/2016.
 */
public class SamplerSlotsFragment extends Fragment {

    public static final String _TAG = SamplerSlotsFragment.class.getSimpleName();
    private GridView gridview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View rootView = inflater.inflate(
                R.layout.sampler_slots_fragment_layout, container, false);

        gridview = (GridView) rootView.findViewById(R.id.gridview);
        gridview.setAdapter(new SanplerSlotsAdapter(getActivity(), Constants.SLOTS));

        return rootView;
    }

    public static SamplerSlotsFragment getInstance() {
        return new SamplerSlotsFragment();
    }
}