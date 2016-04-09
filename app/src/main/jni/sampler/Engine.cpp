//
// Created by stefano on 3/24/2016.
//

#include "Engine.h"

Engine::Engine() {

    SLresult result;

    // create engine
    result = slCreateEngine(&engineObject, 0, NULL, 0, NULL, NULL);
    ErrorChecker::check(result);
    Logger::log("Engine created");

    // realize the engine
    result = (*engineObject)->Realize(engineObject, SL_BOOLEAN_FALSE);
    ErrorChecker::check(result);
    Logger::log("Engine realized");

    // get the engine interface, which is needed in order to create other objects
    result = (*engineObject)->GetInterface(engineObject, SL_IID_ENGINE,
                                           &engineItf);
    ErrorChecker::check(result);
    Logger::log("Got interface");
}

Engine::~Engine() { }

SLEngineItf *Engine::getEngine() {
    return &engineItf;
}