//
// Created by stefano on 3/20/2016.
//

#ifndef JNIEXAMPLE_CONSOLE_H
#define JNIEXAMPLE_CONSOLE_H

#include "interfaces/IConsole.h"
#include "interfaces/IMixer.h"
#include "interfaces/IPlayersController.h"
#include "interfaces/IEngine.h"
#include "Options.h"
#include "Engine.h"
#include "Mixer.h"
#include "PlayersController.h"

class Console : public IConsole {

public:
    Console(Options options);
    ~Console();

private:
    IEngine *_engine;
    IMixer *_mixer;
    IPlayersController *_playerController;
};

#endif //JNIEXAMPLE_CONSOLE_H
