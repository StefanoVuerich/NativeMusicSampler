package com.vuric.nativemusicsampler.interfaces;

import com.vuric.nativemusicsampler.models.PlayerModel;

/**
 * Created by stefano on 4/9/2016.
 */
public interface IConsole {

    void init();
    void shutdown();
    IMixer getMixer();
    IPlayer[] getPlayers();
    PlayerModel[] getPlayerModels();
}
