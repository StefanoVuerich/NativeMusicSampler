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

using namespace std;

class Player : public IPlayer{

public:
    Player(SLEngineItf &engine, SLObjectItf &obj);
    ~Player();
    void load(string fileName);
    void play();

private:
    SLObjectItf _bufferQueuePlayerObject;
    SLPlayItf _playItf;
    SLBufferQueueItf _bufferQueueItf;
};


#endif //JNIEXAMPLE_PLAYER_H
