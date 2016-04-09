//
// Created by stefano on 3/24/2016.
//

#ifndef JNIEXAMPLE_IENGINE_H
#define JNIEXAMPLE_IENGINE_H

#include <SLES/OpenSLES_Android.h>

class IEngine {
public:
    virtual SLEngineItf *getEngine() = 0;
};

#endif //JNIEXAMPLE_IENGINE_H
