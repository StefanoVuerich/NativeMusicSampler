//
// Created by stefano on 3/20/2016.
//

#ifndef JNIEXAMPLE_IPLAYERSCONTROLLER_H
#define JNIEXAMPLE_IPLAYERSCONTROLLER_H

class IPlayersController {

public:
    virtual void play(int playerIndex) = 0;
    virtual void load(int playerIndex) = 0;
};

#endif //JNIEXAMPLE_IPLAYERSCONTROLLER_H
