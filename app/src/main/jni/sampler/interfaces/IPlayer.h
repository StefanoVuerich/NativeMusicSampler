// Created by stefano on 3/16/2016.
//

#ifndef JNIEXAMPLE_IPLAYER_H
#define JNIEXAMPLE_IPLAYER_H

#include <string>
#include <stdio.h>

using namespace std;

class IPlayer {

public:
    virtual void play() = 0;
    virtual void load(string fileName) = 0;
};


#endif //JNIEXAMPLE_IPLAYER_H
