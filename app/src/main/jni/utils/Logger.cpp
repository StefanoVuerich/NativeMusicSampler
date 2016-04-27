//
// Created by stefano on 12/16/2015.
//

#include "Logger.h"

Logger::Logger() { }

Logger::~Logger() { }

void Logger::log(std::string text) {

    __android_log_print(ANDROID_LOG_DEBUG, "OpenSLNative", "SlotsContainerEvt -> %s", text.c_str());
}
