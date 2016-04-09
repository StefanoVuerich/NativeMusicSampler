//
// Created by stefano on 3/24/2016.
//

#ifndef JNIEXAMPLE_ENGINE_H
#define JNIEXAMPLE_ENGINE_H

#include "interfaces/IEngine.h"
#include "../utils/ErrorChecker.h"
#include "../utils/Logger.h"

class Engine : public IEngine {

public:
    Engine();
    ~Engine();
    SLEngineItf *getEngine();

private:
    SLObjectItf engineObject;
    SLEngineItf engineItf;
};


#endif //JNIEXAMPLE_ENGINE_H
