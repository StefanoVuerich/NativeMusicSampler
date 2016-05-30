//
// Created by stefano on 3/20/2016.
//

#include "Console.h"

Console::Console(Options options) {

    _engine = new Engine();
    _mixer = new Mixer(*_engine->getEngine(), options._bandLevels);
    //_playerController = new PlayersController(*_engine->getEngine(), *_mixer->getMixer(), options._playersNumber);

    for(int i = 0; i < options._playersNumber; ++i) {
        Logger::log("Try to create new player");
        Player *tmp = new Player(*_engine->getEngine(), *_mixer->getMixer(), i);
        _players.push_back(tmp);
        Logger::log("Player pushed to vector");
    }
}

Console::~Console() { }

/*void Console::loadSample(int slotIndex, string path) {
    _players[slotIndex]->load(path);
}*/

IPlayer* Console::getPlayer(int index) {

    return _players[index];
}