//
// Created by stefano on 3/24/2016.
//

#include "PlayersController.h"

PlayersController::PlayersController(SLEngineItf &engine, SLObjectItf &mixer, int players) {

    for(int i = 0; i < players; ++i) {
        Logger::log("Try to create new player");
        Player *tmp = new Player(engine, mixer);
        _players.push_back(tmp);
        Logger::log("Player pushed to vector");
    }
}

PlayersController::~PlayersController() { }

void PlayersController::play(int playerIndex) { }
