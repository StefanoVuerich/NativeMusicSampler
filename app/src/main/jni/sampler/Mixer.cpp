//
// Created by stefano on 3/20/2016.
//

#include "Mixer.h"

Mixer::Mixer(SLEngineItf &engine, int *bandLevels) {

    SLresult result;

    // Initialize properties
    const SLInterfaceID ids[] = {};
    const SLboolean req[] = {};
    const int itfNumber = 0;

    // create output mix
    result = (*engine)->CreateOutputMix(engine, &_mixerObject, itfNumber, ids, req);
    ErrorChecker::check(result);
    Logger::log("Mixer created");

    // realize the output mix
    result = (*_mixerObject)->Realize(_mixerObject, SL_BOOLEAN_FALSE);
    ErrorChecker::check(result);
    Logger::log("Mixer realized");
}

Mixer::~Mixer() { }

SLObjectItf* Mixer::getMixer() {
    return &_mixerObject;
}
