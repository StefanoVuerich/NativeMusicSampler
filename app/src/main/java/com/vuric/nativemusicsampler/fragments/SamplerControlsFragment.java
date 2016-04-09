package com.vuric.nativemusicsampler.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vuric.nativemusicsampler.R;

/**
 * Created by stefano on 4/5/2016.
 */
public class SamplerControlsFragment extends Fragment implements View.OnClickListener {

    public final static String _TAG = SamplerControlsFragment.class.getSimpleName();
    private Button mainButton, slotButton, loopButton;
    private Button lastclickeButton = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.sampler_controls_fragment_layout, container, false);

        /*mainButton = (Button) rootView.findViewById(R.id.mainButton);
        mainButton.setOnClickListener(this);

        slotButton = (Button) rootView.findViewById(R.id.slotButton);
        slotButton.setOnClickListener(this);

        loopButton = (Button) rootView.findViewById(R.id.loopButton);
        loopButton.setOnClickListener(this);

        getFragmentManager().beginTransaction()
                .replace(R.id.controlsRightContainer, new MainControlsFragment(), MainControlsFragment._TAG).commit();

        setSelectedButton(mainButton);*/

        return rootView;
    }

    public static SamplerControlsFragment getInstance() {
        return new SamplerControlsFragment();
    }

    @Override
    public void onClick(View v) {

        /*setSelectedButton(v);

        Fragment fragment = null;
        String TAG = "";

        switch (v.getId()) {
            case R.id.mainButton:
                fragment = MainControlsFragment.getInstance();
                TAG = MainControlsFragment._TAG;
                break;
            case R.id.slotButton:
                fragment = SlotControlsFragment.getInstance();
                TAG = SlotControlsFragment._TAG;
                break;
            case R.id.loopButton:
                fragment = LoopFragment.getInstance();
                TAG = LoopFragment._TAG;
                break;
        }
        if (fragment != null && !TAG.equals("") && getFragmentManager().findFragmentByTag(TAG) == null)
            getFragmentManager().beginTransaction().replace(R.id.controlsRightContainer, fragment, TAG).commit();

        if (((MainActivity) getActivity()).isDrawerVisible()) {
            closeDrawer();
        }*/

    }

    private void closeDrawer() {
        //((MainActivity) getActivity()).closeDrawer();
    }

    private void setSelectedButton(View v) {
        if (lastclickeButton != null)
            lastclickeButton.setEnabled(true);

        v.setEnabled(false);
        lastclickeButton = (Button) v;
    }
}