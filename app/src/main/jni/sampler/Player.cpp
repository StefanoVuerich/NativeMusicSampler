//
// Created by stefano on 3/24/2016.
//

#include "Player.h"

Player::Player(SLEngineItf &engine, SLObjectItf &mixer) {

    SLresult result;


    // configure audio source
    SLDataLocator_AndroidSimpleBufferQueue loc_bufq = {SL_DATALOCATOR_ANDROIDSIMPLEBUFFERQUEUE, 2};
    SLDataFormat_PCM format_pcm = {SL_DATAFORMAT_PCM, 1, SL_SAMPLINGRATE_8,
                                   SL_PCMSAMPLEFORMAT_FIXED_16, SL_PCMSAMPLEFORMAT_FIXED_16,
                                   SL_SPEAKER_FRONT_CENTER, SL_BYTEORDER_LITTLEENDIAN};
    SLDataSource audioSrc = {&loc_bufq, &format_pcm};

    // configure audio sink
    SLDataLocator_OutputMix loc_outmix = {SL_DATALOCATOR_OUTPUTMIX, mixer};
    SLDataSink audioSnk = {&loc_outmix, NULL};

    // create audio player
    const SLInterfaceID ids[2] = {SL_IID_BUFFERQUEUE, SL_IID_VOLUME};
    const SLboolean req[2] = {SL_BOOLEAN_TRUE, SL_BOOLEAN_TRUE};

    result = (*engine)->CreateAudioPlayer(engine, &_bufferQueuePlayerObject, &audioSrc, &audioSnk,
                                                        2, ids, req);
    ErrorChecker::check(result);
    Logger::log("Player bq creato");

    // realize the player
    result = (*_bufferQueuePlayerObject)->Realize(_bufferQueuePlayerObject, SL_BOOLEAN_FALSE);
    ErrorChecker::check(result);
    Logger::log("Player bq realizzato");

    // get the play interface
    result = (*_bufferQueuePlayerObject)->GetInterface(_bufferQueuePlayerObject, SL_IID_PLAY, &_playItf);
    ErrorChecker::check(result);
    Logger::log("Got play interface");

    // get the buffer queue interface
    result = (*_bufferQueuePlayerObject)->GetInterface(_bufferQueuePlayerObject, SL_IID_BUFFERQUEUE,
                                             &_bufferQueueItf);
    ErrorChecker::check(result);
    Logger::log("Got buffer interface");

    // create empty buffer
    #define BUFFER_SIZE 1024
    short empy_buffer[BUFFER_SIZE];
    for (int i = 0; i < BUFFER_SIZE; ++i) {
        empy_buffer[i] = 32768 - ((i % 100) * 660);
    }

    // enqueue empty buffer
    result = (*_bufferQueueItf)->Enqueue(_bufferQueueItf, empy_buffer, BUFFER_SIZE);
    ErrorChecker::check(result);
    Logger::log("Empty buffer enqueued");

    // play buffer
    result = (*_playItf)->SetPlayState(_playItf, SL_PLAYSTATE_PLAYING);
    ErrorChecker::check(result);
    Logger::log("Played empty buffer");
}

Player::~Player() { }

void Player::load(string fileName) { }

void Player::play() { }
