package com.vuric.nativemusicsampler.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.squareup.otto.Subscribe;
import com.vuric.nativemusicsampler.BusStation;
import com.vuric.nativemusicsampler.R;
import com.vuric.nativemusicsampler.activities.MainActivity;
import com.vuric.nativemusicsampler.enums.PlayState;
import com.vuric.nativemusicsampler.enums.AppLayoutState;
import com.vuric.nativemusicsampler.events.SampleLoadedEvt;
import com.vuric.nativemusicsampler.layouts.SlotsContainerRelativeLayout;
import com.vuric.nativemusicsampler.models.PlayerModel;
import com.vuric.nativemusicsampler.models.GlobalPlayersState;
import com.vuric.nativemusicsampler.models.SampleModel;
import com.vuric.nativemusicsampler.utils.Constants;

/**
 * Created by stefano on 4/5/2016.
 */
public class SamplerSlotsFragment extends Fragment {

    public static final String _TAG = SamplerSlotsFragment.class.getSimpleName();
    public static final String PLAYERS_STATE = "PLAYERS_STATE";
    private AppLayoutState _state;
    private FrameLayout _samplerSlotsBaseContainer;
    private SlotsContainerRelativeLayout _slotsContainerRelativeLayout;
    private GlobalPlayersState _playerState;

    @Override
    public void onResume() {
        super.onResume();

        BusStation.getBus().register(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle != null) {
            _state = (AppLayoutState)bundle.get(MainActivity.APP_LAYOUT_STATE);
            _playerState = (GlobalPlayersState) bundle.get(PLAYERS_STATE);
        }

        if(_playerState == null) {

            PlayerModel[] models = new PlayerModel[Constants.SLOTS];

            for(int i = 0; i < Constants.SLOTS; ++i) {

                PlayerModel tmp = new PlayerModel();
                tmp.setID(i);
                tmp.setState(PlayState.STOP);

                models[i] = tmp;
            }

            _playerState = new GlobalPlayersState(models);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(PLAYERS_STATE, _playerState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View rootView = inflater.inflate(
                R.layout.sampler_slots_fragment_layout, container, false);

        _samplerSlotsBaseContainer = (FrameLayout) rootView.findViewById(R.id.samplerSlotsBaseContainer);
        _slotsContainerRelativeLayout = new SlotsContainerRelativeLayout(getActivity(), Constants.SLOTS, _state, _playerState);
        _samplerSlotsBaseContainer.addView(_slotsContainerRelativeLayout);

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

        BusStation.getBus().unregister(this);
    }

    @Subscribe
    public void receiveMessage(SampleLoadedEvt evt) {

       PlayerModel model = _playerState.getPlayersModel()[evt.getID()];

        model.setLoaded(true);

        SampleModel sampleObj =  new SampleModel();
        sampleObj.setName(evt.getTitle());

        model.setSampleInfo(sampleObj);
    }

    public static SamplerSlotsFragment getInstance() {
        return new SamplerSlotsFragment();
    }

    public void setState(AppLayoutState state) {

        _state = state;
        _slotsContainerRelativeLayout.setState(state);
        _slotsContainerRelativeLayout.invalidate();
    }
}