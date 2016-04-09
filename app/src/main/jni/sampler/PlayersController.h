//
// Created by stefano on 3/24/2016.
//

#ifndef JNIEXAMPLE_PLAYERSCONTROLLER_H
#define JNIEXAMPLE_PLAYERSCONTROLLER_H

#include "interfaces/IPlayersController.h"
#include "interfaces/IPlayer.h"
#include "Player.h"
#include <vector>

class PlayersController : public IPlayersController{

public:
    PlayersController(SLEngineItf &engine, SLObjectItf &mixer, int players);
    ~PlayersController();
    void play(int playerIndex);

private:
    std::vector<IPlayer*> _players;
};


#endif //JNIEXAMPLE_PLAYERSCONTROLLER_H
