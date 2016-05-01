//
// Created by stefano on 3/16/2016.
//

#ifndef JNIEXAMPLE_ICONSOLE_H
#define JNIEXAMPLE_ICONSOLE_H

#include <string>
#include <stdio.h>
#include "IPlayersController.h"
#include "IPlayer.h"

using namespace std;

class IConsole {

public:
    virtual IPlayer *getPlayer(int index) = 0;
    //virtual void loadSample(int slotIndex, string path) = 0;
};


#endif //JNIEXAMPLE_ICONSOLE_H
