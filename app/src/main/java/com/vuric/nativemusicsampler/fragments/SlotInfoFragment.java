package com.vuric.nativemusicsampler.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vuric.nativemusicsampler.R;
import com.vuric.nativemusicsampler.models.PlayerModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class SlotInfoFragment extends Fragment {

    public static final String _TAG = SlotInfoFragment.class.getSimpleName();
    private PlayerModel _model;

    public static Fragment newInstance(PlayerModel playerModel) {

        Bundle args = new Bundle();
        args.putSerializable(SamplerControlsFragment.PLAYER_MODEL, playerModel);
        Fragment fragment = new SlotInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _model = (PlayerModel) getArguments().getSerializable(SamplerControlsFragment.PLAYER_MODEL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.slot_info_fragment_layout, container, false);

        if(_model.getSampleInfo() != null) {

            TextView artist = (TextView) v.findViewById(R.id.sampleArtist);
            artist.setText(_model.getSampleInfo().getArtist());

            TextView title = (TextView) v.findViewById(R.id.sampleTitle);
            title.setText(_model.getSampleInfo().getTitle());

            TextView duration = (TextView) v.findViewById(R.id.sampleDuration);
            duration.setText("" + _model.getSampleInfo().getHours() + "." +
                    _model.getSampleInfo().getMinutes() + "." +
                    _model.getSampleInfo().getSeconds());

            TextView format = (TextView) v.findViewById(R.id.sampleFormat);
            format.setText("" + _model.getSampleInfo().getFormat());

            TextView bitrate = (TextView) v.findViewById(R.id.sampleBitrate);
            bitrate.setText("" + _model.getSampleInfo().getBitrate());

            TextView size = (TextView) v.findViewById(R.id.sampleSize);
            size.setText("" + _model.getSampleInfo().getSize());
        }

        return v;
    }
}
