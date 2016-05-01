//
// Created by stefano on 3/24/2016.
//

#include "Player.h"

//extern "C" {
    static void playerReadyCallback(SLPrefetchStatusItf caller, void *settings, SLuint32 event) {

        Logger::log("Callback called");
    }
//}

Player::Player(SLEngineItf &engine, SLObjectItf &mixer) {

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

void Player::load(string fileName) {

    int settings[3];
    Logger::log("Loading");
    Logger::log(fileName.c_str());

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

    result = (*_player)->GetInterface(_player,SL_IID_SEEK, (void*) &_seekInterface);
    ErrorChecker::check(result);

    result = (*_player)->GetInterface(_player,SL_IID_VOLUME, (void*) &_volumeInterface);
    ErrorChecker::check(result);

    result = (*_player)->GetInterface(_player,SL_IID_PREFETCHSTATUS, (void*) &_prefetchStatusInterface);
    ErrorChecker::check(result);

    result = (*_prefetchStatusInterface)->SetCallbackEventsMask(_prefetchStatusInterface, SL_PREFETCHSTATUS_SUFFICIENTDATA);
    ErrorChecker::check(result);

    result = (*_prefetchStatusInterface)->RegisterCallback(_prefetchStatusInterface, playerReadyCallback, (void*) &settings);
    ErrorChecker::check(result);

    result = (*_player)->GetInterface(_player,SL_IID_PLAY, (void*) &_playInterface);
    ErrorChecker::check(result);

    if (FILE *file = fopen(fileName.c_str(), "r")) {
        fclose(file);
    } else {
        Logger::log("File non esiste");
    }



    Logger::log("Loaded");

    loaded = true;
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

