package com.vuric.nativemusicsampler.events;

import com.vuric.nativemusicsampler.models.PlayerModel;

public class SampleSlotSelectedEvt {

    private PlayerModel _playerModel;

    public SampleSlotSelectedEvt(PlayerModel playerModel) {

        _playerModel = playerModel;
    }

    public PlayerModel getPlayerModel() {
        return _playerModel;
    }
}
