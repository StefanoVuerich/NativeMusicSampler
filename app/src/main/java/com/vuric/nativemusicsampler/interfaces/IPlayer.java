package com.vuric.nativemusicsampler.interfaces;

import com.vuric.nativemusicsampler.enums.PlayState;
import com.vuric.nativemusicsampler.models.PlayerModel;
import com.vuric.nativemusicsampler.models.SampleModel;

public interface IPlayer {

    boolean play();
    boolean pause();
    boolean stop();
    boolean load(String path);
    void unload();
    PlayState getPlayState();
    void enableLoop();
    void disableeLoop();
    PlayerModel getPlayerModel();
    void setSample(SampleModel model);
}
