//
// Created by stefano on 3/24/2016.
//

#ifndef JNIEXAMPLE_PLAYER_H
#define JNIEXAMPLE_PLAYER_H

#include <string>
#include <stdio.h>
#include <SLES/OpenSLES.h>
#include "interfaces/IPlayer.h"
#include "../utils/Logger.h"
#include "../utils/ErrorChecker.h"
#include "../utils/JavaLinker.h"

using namespace std;

class Player : public IPlayer{

public:
    Player(SLEngineItf &engine, SLObjectItf &mixer, int id);
    ~Player();
    void init();
    void play();
    void pause();
    void stop();
    bool load(string fileName);
    void unload();
    bool isLoaded();

protected:
    int _id;
    bool loaded;
    int state;
    SLEngineItf _engine;
    SLObjectItf _mixer;
    SLObjectItf _player;
    SLBufferQueueItf _bufferQueueItf;
    SLPlayItf _playInterface;
    SLSeekItf _seekInterface;
    SLVolumeItf _volumeInterface;
    SLPrefetchStatusItf _prefetchStatusInterface;
};

void playerReadyCallback(SLPrefetchStatusItf caller, void *settings, SLuint32 event);
void playerStatusUpdate (SLPlayItf caller, void *pContext, SLuint32 event);

#endif //JNIEXAMPLE_PLAYER_H
