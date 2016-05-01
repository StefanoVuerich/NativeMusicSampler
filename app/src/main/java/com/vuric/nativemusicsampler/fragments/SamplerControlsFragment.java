package com.vuric.nativemusicsampler.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vuric.nativemusicsampler.R;
import com.vuric.nativemusicsampler.models.PlayerModel;

public class SamplerControlsFragment extends Fragment implements View.OnClickListener {

    public final static String _TAG = SamplerControlsFragment.class.getSimpleName();
    public static final String PLAYER_MODEL = "PLAYER_MODEL";
    private Button infoButton, loadButton, volumeButton, pitchButton, loopButton, effectsButton;
    private Button lastclickeButton = null;
    private PlayerModel _playerModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _playerModel = (PlayerModel) getArguments().getSerializable(PLAYER_MODEL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.sampler_controls_fragment_layout, container, false);

        infoButton = (Button) rootView.findViewById(R.id.infoButton);
        infoButton.setOnClickListener(this);

        loadButton = (Button) rootView.findViewById(R.id.loadButton);
        loadButton.setOnClickListener(this);

        volumeButton = (Button) rootView.findViewById(R.id.volumeButton);
        volumeButton.setOnClickListener(this);

        pitchButton = (Button) rootView.findViewById(R.id.pitchButton);
        pitchButton.setOnClickListener(this);

        loopButton = (Button) rootView.findViewById(R.id.loopButton);
        loopButton.setOnClickListener(this);

        effectsButton = (Button) rootView.findViewById(R.id.effectsButton);
        effectsButton.setOnClickListener(this);

        getFragmentManager().beginTransaction()
                .replace(R.id.controlsContainer, SlotInfoFragment.newInstance(), SlotInfoFragment._TAG).commit();

        setSelectedButton(infoButton);

        return rootView;
    }

    public static Fragment getInstance(PlayerModel playerModel) {

        Bundle args = new Bundle();
        args.putSerializable(PLAYER_MODEL, playerModel);

        Fragment fragment = new SamplerControlsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onClick(View v) {

        setSelectedButton(v);

        Fragment fragment = null;
        String TAG = "";

        switch (v.getId()) {
            case R.id.infoButton:
                fragment = SlotInfoFragment.newInstance();
                TAG = SlotInfoFragment._TAG;
                break;
            case R.id.loadButton:
                fragment = SlotLoadFragment.newInstance(_playerModel.getID());
                TAG = SlotLoadFragment._TAG;
                break;
            case R.id.volumeButton:
                fragment = SlotVolumeFragment.newInstance();
                TAG = SlotVolumeFragment._TAG;
                break;

            case R.id.pitchButton:
                fragment = SlotPitchFragment.newInstance();
                TAG = SlotPitchFragment._TAG;
                break;

            case R.id.loopButton:
                fragment = SlotLoopFragment.newInstance();
                TAG = SlotLoopFragment._TAG;
                break;

            case R.id.effectsButton:
                fragment = SlotEffectsFragment.newInstance();
                TAG = SlotEffectsFragment._TAG;
                break;
        }

        getFragmentManager().beginTransaction().replace(R.id.controlsContainer, fragment, TAG).commit();
    }

    private void setSelectedButton(View v) {
        if (lastclickeButton != null)
            lastclickeButton.setEnabled(true);

        v.setEnabled(false);
        lastclickeButton = (Button) v;
    }

    public static Fragment getInstance() {

        return new SamplerControlsFragment();
    }
}