//
// Created by stefano on 3/20/2016.
//

#ifndef JNIEXAMPLE_CONSOLE_H
#define JNIEXAMPLE_CONSOLE_H

#include "interfaces/IConsole.h"
#include "interfaces/IMixer.h"
#include "interfaces/IPlayer.h"
#include "interfaces/IEngine.h"
#include "Options.h"
#include "Engine.h"
#include "Mixer.h"
#include "Player.h"
#include <vector>

class Console : public IConsole {

public:
    Console(Options options);
    ~Console();

private:
    IEngine *_engine;
    IMixer *_mixer;
    IPlayersController *_playerController;
    std::vector<IPlayer*> _players;
};

#endif //JNIEXAMPLE_CONSOLE_H
