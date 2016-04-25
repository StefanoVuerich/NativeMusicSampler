package com.vuric.nativemusicsampler.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.vuric.nativemusicsampler.R;
import com.vuric.nativemusicsampler.enums.SlotsContainerState;
import com.vuric.nativemusicsampler.layouts.SlotsContainerRelativeLayout;
import com.vuric.nativemusicsampler.utils.Constants;

/**
 * Created by stefano on 4/5/2016.
 */
public class SamplerSlotsFragment extends Fragment {

    public static final String _TAG = SamplerSlotsFragment.class.getSimpleName();
    private SlotsContainerState _state;
    FrameLayout l;
    SlotsContainerRelativeLayout rl;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Bundle b = getArguments();
        if(b != null) {
            _state = (SlotsContainerState)b.get("STATE");
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View rootView = inflater.inflate(
                R.layout.sampler_slots_fragment_layout, container, false);

        /*int width;
        int height;
        _gridview = (GridView) rootView.findViewById(R.id.gridview);
        _gridview.setAdapter(new SanplerSlotsAdapter(getActivity()));
        _gridview.setOnTouchListener(new SlotsContainerGestureListener(getActivity()));*/

        l = (FrameLayout) rootView.findViewById(R.id.samplerSlotsBaseContainer);
        rl = new SlotsContainerRelativeLayout(getActivity(), Constants.SLOTS, _state);
        l.addView(rl);

        return rootView;
    }

    public static SamplerSlotsFragment getInstance() {
        return new SamplerSlotsFragment();
    }

    public void setState(SlotsContainerState state) {
        if(state != _state) {
            _state = state;

            l.removeAllViewsInLayout();
            l.addView(new SlotsContainerRelativeLayout(getActivity(), Constants.SLOTS, _state));
            l.invalidate();
        }
    }
}