//
// Created by stefano on 3/20/2016.
//

#include "Console.h"

Console::Console(Options options) {

    _engine = new Engine();
    _mixer = new Mixer(*_engine->getEngine(), options._bandLevels);
    _playerController = new PlayersController(*_engine->getEngine(), *_mixer->getMixer(), options._playersNumber);
}

Console::~Console() { }
