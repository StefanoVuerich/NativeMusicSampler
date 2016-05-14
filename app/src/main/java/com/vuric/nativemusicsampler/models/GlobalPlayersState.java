package com.vuric.nativemusicsampler.models;

import java.io.Serializable;

/**
 * Created by stefano on 5/11/2016.
 */
public class GlobalPlayersState implements Serializable{

    private PlayerModel[] _playersModel;

    public GlobalPlayersState(PlayerModel[] playersModel) {
        _playersModel = playersModel;
    }

    public PlayerModel[] getPlayersModel() {
        return _playersModel;
    }
}
