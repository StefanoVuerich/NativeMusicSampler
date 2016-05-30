//
// Created by stefano on 3/24/2016.
//

#include "Player.h"
#include "../utils/JavaLinker.h"


void playerReadyCallback(SLPrefetchStatusItf caller, void *settings, SLuint32 event) {

    JavaLinker::getInstance()->invoceCallback("sampleLoaded", settings);

    //(*_playInterface)->SetCallbackEventsMask(_playInterface, SL_PLAYEVENT_HEADATEND);
}

void playerStatusUpdate (SLPlayItf caller, void *pContext, SLuint32 event) {

    SLresult result;
    SLPlayItf playItf = (SLPlayItf)*(reinterpret_cast<SLPlayItf*>(pContext));

    SLmillisecond pMsec;
    result = (*playItf)->GetMarkerPosition(playItf, &pMsec);
    ErrorChecker::check(result);

    SLmillisecond duration;
    (*playItf)->GetDuration(playItf, &duration);
    ErrorChecker::check(result);

    SLmillisecond newPosition;

    switch(event) {

        case SL_PLAYEVENT_HEADATMARKER:
            Logger::log("SL_PLAYEVENT_HEADATMARKER");

            newPosition = (SLmillisecond)pMsec + 100;

            result = (*playItf)->SetMarkerPosition(playItf, newPosition);
            ErrorChecker::check(result);

            if(newPosition >= duration - 100) {
                result = (*playItf)->SetCallbackEventsMask(playItf, SL_PLAYEVENT_HEADATEND);
                ErrorChecker::check(result);
            }

            break;

        case SL_PLAYEVENT_HEADATEND:
            Logger::log("SL_PLAYEVENT_HEADATEND");

            result = (*playItf)->SetMarkerPosition(playItf, 100);
            ErrorChecker::check(result);

            result = (*playItf)->SetCallbackEventsMask(playItf, SL_PLAYEVENT_HEADATMARKER);
            ErrorChecker::check(result);

            break;
    }
}

Player::Player(SLEngineItf &engine, SLObjectItf &mixer, int id) {

    _id = id;
    _engine = engine;
    _mixer = mixer;

}

Player::~Player() { }

void Player::init() {}

void Player::play() {

    SLresult result;
    if(state == 0 || state == 2) {
        result = (*_playInterface)->SetPlayState(_playInterface,SL_PLAYSTATE_PLAYING);
        ErrorChecker::check(result);
    } else {
        result = (*_playInterface)->SetPlayState(_playInterface,SL_PLAYSTATE_STOPPED);
        ErrorChecker::check(result);
        result = (*_playInterface)->SetPlayState(_playInterface,SL_PLAYSTATE_PLAYING);
        ErrorChecker::check(result);
    }
    state = 1;
    Logger::log("Sample played");
}

void Player::pause() {

    if(state == 1) {
        (*_playInterface)->SetPlayState(_playInterface,SL_PLAYSTATE_PAUSED);
        state = 2;
    }
}

void Player::stop() {

    if(state == 1 || state == 2) {
        (*_playInterface)->SetPlayState(_playInterface,SL_PLAYSTATE_STOPPED);
        state = 0;
    }
}

bool Player::load(string fileName) {

    Logger::log("Loading" + fileName);
    int settings[3];

    SLresult result;

    SLDataLocator_URI fileLoc = { SL_DATALOCATOR_URI, (SLchar *)fileName.c_str() };
    SLDataFormat_MIME mime = { SL_DATAFORMAT_MIME, NULL, SL_CONTAINERTYPE_UNSPECIFIED };
    SLDataSource audioSrcUri = { &fileLoc, &mime };
    const SLInterfaceID ids[3] = { SL_IID_SEEK, SL_IID_VOLUME, SL_IID_PREFETCHSTATUS };
    const SLboolean req[3] = { SL_BOOLEAN_TRUE, SL_BOOLEAN_TRUE, SL_BOOLEAN_TRUE };
    SLDataLocator_OutputMix loc_outmix = { SL_DATALOCATOR_OUTPUTMIX, _mixer};
    SLDataSink audioSnk = { &loc_outmix, NULL };

    result = (*_engine)->CreateAudioPlayer(_engine, &_player, &audioSrcUri, &audioSnk, 3, ids, req);
    ErrorChecker::check(result);

    result = (*_player)->Realize(_player,SL_BOOLEAN_FALSE);
    ErrorChecker::check(result);

    // Seek Interface

    result = (*_player)->GetInterface(_player,SL_IID_SEEK, (void*) &_seekInterface);
    ErrorChecker::check(result);

    // Volume Interface

    result = (*_player)->GetInterface(_player,SL_IID_VOLUME, (void*) &_volumeInterface);
    ErrorChecker::check(result);

    // PrefetchStatus Interface

    result = (*_player)->GetInterface(_player,SL_IID_PREFETCHSTATUS, (void*) &_prefetchStatusInterface);
    ErrorChecker::check(result);

    result = (*_prefetchStatusInterface)->SetCallbackEventsMask(_prefetchStatusInterface, SL_PREFETCHSTATUS_SUFFICIENTDATA);
    ErrorChecker::check(result);

    result = (*_prefetchStatusInterface)->RegisterCallback(_prefetchStatusInterface, playerReadyCallback, (void*) &_id);
    ErrorChecker::check(result);

    // Play Interface

    result = (*_player)->GetInterface(_player,SL_IID_PLAY, (void*) &_playInterface);
    ErrorChecker::check(result);

    result = (*_playInterface)->RegisterCallback(_playInterface, playerStatusUpdate, reinterpret_cast<void*>(&_playInterface));
    ErrorChecker::check(result);

    result = (*_playInterface)->SetPositionUpdatePeriod(_playInterface,100);
    ErrorChecker::check(result);

    result = (*_playInterface)->SetMarkerPosition(_playInterface, 100);
    ErrorChecker::check(result);

    result = (*_playInterface)->SetCallbackEventsMask(_playInterface, SL_PLAYEVENT_HEADATMARKER);
    ErrorChecker::check(result);

    /*if (FILE *file = fopen(fileName.c_str(), "r")) {
        fclose(file);
    } else {
        Logger::log("File non esiste");
    }

    Logger::log("Loaded");*/

    loaded = true;

    return true;
}

void Player::unload() {

    //code to unload file
    loaded = false;
}

bool Player::isLoaded() {

    if(loaded) {
        return true;
    } else {
        return false;
    }
}

