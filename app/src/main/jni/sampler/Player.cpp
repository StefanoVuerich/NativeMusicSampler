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

    if(event & SL_PLAYEVENT_HEADATEND) {

        JavaLinker::getInstance()->invoceCallback("playerStatusUpdate", pContext);
    }
}

Player::Player(SLEngineItf &engine, SLObjectItf &mixer, int id) {

    _id = id;
    _engine = engine;
    _mixer = mixer;

}

Player::~Player() { }

void Player::init() {}

bool Player::play() {

    _result = (*_playInterface)->SetPlayState(_playInterface,SL_PLAYSTATE_PLAYING);
    ErrorChecker::check(_result);

    return true;
}

bool Player::pause() {

    _result = (*_playInterface)->SetPlayState(_playInterface,SL_PLAYSTATE_PAUSED);
    ErrorChecker::check(_result);

    return true;
}

bool Player::stop() {

    _result = (*_playInterface)->SetPlayState(_playInterface,SL_PLAYSTATE_STOPPED);
    ErrorChecker::check(_result);

    return true;
}

bool Player::load(string fileName) {

    Logger::log("Loading" + fileName);
    int settings[3];

    SLDataLocator_URI fileLoc = { SL_DATALOCATOR_URI, (SLchar *)fileName.c_str() };
    SLDataFormat_MIME mime = { SL_DATAFORMAT_MIME, NULL, SL_CONTAINERTYPE_UNSPECIFIED };
    SLDataSource audioSrcUri = { &fileLoc, &mime };
    const SLInterfaceID ids[3] = { SL_IID_SEEK, SL_IID_VOLUME, SL_IID_PREFETCHSTATUS };
    const SLboolean req[3] = { SL_BOOLEAN_TRUE, SL_BOOLEAN_TRUE, SL_BOOLEAN_TRUE };
    SLDataLocator_OutputMix loc_outmix = { SL_DATALOCATOR_OUTPUTMIX, _mixer};
    SLDataSink audioSnk = { &loc_outmix, NULL };

    _result = (*_engine)->CreateAudioPlayer(_engine, &_player, &audioSrcUri, &audioSnk, 3, ids, req);
    ErrorChecker::check(_result);

    _result = (*_player)->Realize(_player,SL_BOOLEAN_FALSE);
    ErrorChecker::check(_result);

    // Seek Interface

    _result = (*_player)->GetInterface(_player,SL_IID_SEEK, (void*) &_seekInterface);
    ErrorChecker::check(_result);

    // Volume Interface

    _result = (*_player)->GetInterface(_player,SL_IID_VOLUME, (void*) &_volumeInterface);
    ErrorChecker::check(_result);

    // PrefetchStatus Interface

    _result = (*_player)->GetInterface(_player,SL_IID_PREFETCHSTATUS, (void*) &_prefetchStatusInterface);
    ErrorChecker::check(_result);

    _result = (*_prefetchStatusInterface)->SetCallbackEventsMask(_prefetchStatusInterface, SL_PREFETCHSTATUS_SUFFICIENTDATA);
    ErrorChecker::check(_result);

    _result = (*_prefetchStatusInterface)->RegisterCallback(_prefetchStatusInterface, playerReadyCallback, (void*) &_id);
    ErrorChecker::check(_result);

    // Play Interface

    _result = (*_player)->GetInterface(_player,SL_IID_PLAY, (void*) &_playInterface);
    ErrorChecker::check(_result);

    _result = (*_playInterface)->RegisterCallback(_playInterface, playerStatusUpdate, reinterpret_cast<void*>(&_id));
    ErrorChecker::check(_result);

    _result = (*_playInterface)->SetCallbackEventsMask(_playInterface, SL_PLAYEVENT_HEADATEND);
    ErrorChecker::check(_result);

    /*if (FILE *file = fopen(fileName.c_str(), "r")) {
        fclose(file);
    } else {
        Logger::log("File non esiste");
    }

    Logger::log("Loaded");*/

    return true;
}

bool Player::setVolume(int level) {

    int attenuation = (100 - level);
    SLmillibel nextLevel;

    switch(attenuation) {

        case 0: nextLevel = 0;
            break;

        case 100: nextLevel = SL_MILLIBEL_MIN;
            break;

        default: nextLevel = (SL_MILLIBEL_MIN / 100) * (attenuation /10);
            break;
    }

    std::ostringstream filename;
    filename << "Volume native: " << (int)nextLevel;

    string s = filename.str();

    Logger::log(s);

    _result = (*_volumeInterface)->SetVolumeLevel(_volumeInterface, nextLevel);
}

void Player::unload() {

    //code to unload file
}

bool Player::isLoaded() {

    return true;
}

