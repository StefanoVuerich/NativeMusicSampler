//
// Created by stefano on 12/16/2015.
//

#include <android/log.h>
#include <string>

using namespace std;

#ifndef JNIEXAMPLE_LOGGER_H
#define JNIEXAMPLE_LOGGER_H

class Logger {

public:
    Logger();
    ~Logger();
    static void log(string text);
};

#endif //JNIEXAMPLE_LOGGER_H
