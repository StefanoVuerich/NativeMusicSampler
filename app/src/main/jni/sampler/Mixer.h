//
// Created by stefano on 3/20/2016.
//

#ifndef JNIEXAMPLE_MIXER_H
#define JNIEXAMPLE_MIXER_H

#include <SLES/OpenSLES.h>
#include "../utils/Logger.h"
#include "../utils/ErrorChecker.h"
#include "interfaces/IMixer.h"

class Mixer : public IMixer{

public:
    Mixer(SLEngineItf &engine, short *bandLevels);
    ~Mixer();
    SLObjectItf *getMixer();

private:
    SLEngineItf _engine;
    SLObjectItf _mixerObject;
};


#endif //JNIEXAMPLE_MIXER_H
