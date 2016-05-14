package com.vuric.nativemusicsampler.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.percent.PercentFrameLayout;
import android.support.percent.PercentLayoutHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.squareup.otto.Subscribe;
import com.vuric.nativemusicsampler.BusStation;
import com.vuric.nativemusicsampler.R;
import com.vuric.nativemusicsampler.enums.AppLayoutState;
import com.vuric.nativemusicsampler.events.SampleLoadedEvt;
import com.vuric.nativemusicsampler.models.PlayerModel;
import com.vuric.nativemusicsampler.models.SampleModel;

public class SamplerControlsFragment extends Fragment implements View.OnClickListener {

    public final static String _TAG = SamplerControlsFragment.class.getSimpleName();
    public static final String PLAYER_MODEL = "PLAYER_MODEL";
    private Button infoButton, loadButton, volumeButton, pitchButton, loopButton, effectsButton;
    private Button lastclickeButton;
    private PlayerModel _playerModel;
    private AppLayoutState _state;
    private int _currentViewId;
    private View _rootView;

    @Override
    public void onResume() {
        super.onResume();

        BusStation.getBus().register(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _playerModel = (PlayerModel) getArguments().getSerializable(PLAYER_MODEL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        _rootView = inflater.inflate(R.layout.sampler_controls_fragment_layout, container, false);

        infoButton = (Button) _rootView.findViewById(R.id.infoButton);
        infoButton.setOnClickListener(this);

        loadButton = (Button) _rootView.findViewById(R.id.loadButton);
        loadButton.setOnClickListener(this);

        volumeButton = (Button) _rootView.findViewById(R.id.volumeButton);
        volumeButton.setOnClickListener(this);

        pitchButton = (Button) _rootView.findViewById(R.id.pitchButton);
        pitchButton.setOnClickListener(this);

        loopButton = (Button) _rootView.findViewById(R.id.loopButton);
        loopButton.setOnClickListener(this);

        effectsButton = (Button) _rootView.findViewById(R.id.effectsButton);
        effectsButton.setOnClickListener(this);

        setSelectedButton(infoButton);
        setSelectedFragment(infoButton.getId());
        _currentViewId = infoButton.getId();

        return _rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

        BusStation.getBus().unregister(this);
    }

    @Subscribe
    public void receiveMessage(SampleLoadedEvt evt) {

        SampleModel info = new SampleModel();
        info.setName(evt.getTitle());
        info.setSize(evt.getSize());

        _playerModel.setSampleInfo(info);
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
        setSelectedFragment(v.getId());
        _currentViewId = v.getId();
    }

    private void setSelectedFragment(int viewId) {
        Fragment fragment = null;
        String TAG = "";

        switch (viewId) {
            case R.id.infoButton:
                fragment = SlotInfoFragment.newInstance(_playerModel);
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

        getFragmentManager().beginTransaction().replace(R.id.leftControlsContainer, fragment, TAG).commit();
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

    public void setState(AppLayoutState state) {

        _state = state;

        LinearLayout leftControls = (LinearLayout) _rootView.findViewById(R.id.leftControlsContainer);
        LinearLayout rightControls = (LinearLayout) _rootView.findViewById(R.id.rightControlsContainer);

        PercentFrameLayout.LayoutParams leftParams = (PercentFrameLayout.LayoutParams) leftControls.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo leftInfo = leftParams.getPercentLayoutInfo();

        PercentFrameLayout.LayoutParams rightParams = (PercentFrameLayout.LayoutParams) rightControls.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo rightInfo = rightParams.getPercentLayoutInfo();

        if(state == AppLayoutState.OPEN) {

            leftInfo.widthPercent = 1.0f;
            leftInfo.heightPercent = 1.0f;
            leftControls.requestLayout();
            rightInfo.widthPercent = 0.0f;
            rightInfo.heightPercent = 1.0f;
            rightControls.requestLayout();
            getFragmentManager().beginTransaction().replace(R.id.leftControlsContainer, SlotVolumeFragment.newInstance(), SlotVolumeFragment._TAG).commit();
        } else {
            leftInfo.widthPercent = 0.7f;
            leftInfo.heightPercent = 1.0f;
            leftControls.requestLayout();
            rightInfo.widthPercent = 0.3f;
            rightInfo.heightPercent = 1.0f;
            rightControls.requestLayout();
            setSelectedFragment(_currentViewId);
        }
    }
}