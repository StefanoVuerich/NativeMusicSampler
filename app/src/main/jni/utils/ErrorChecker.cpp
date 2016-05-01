//
// Created by stefano on 12/15/2015.
//

#include "ErrorChecker.h"

ErrorChecker::ErrorChecker() {

    __android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE Error checker created");
}

ErrorChecker::~ErrorChecker() { }

void ErrorChecker::check(SLresult result) {

    if (SL_RESULT_SUCCESS == result) {
        __android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE --SUCCESS--");
    }
    else if (SL_RESULT_PERMISSION_DENIED != result) {
        __android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE --DENIED--");
    }
    else if (SL_RESULT_CONTENT_CORRUPTED != result) {
        __android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE --CORRUPTED--");
    }
    else if (SL_RESULT_SUCCESS != result) {
        __android_log_print(ANDROID_LOG_DEBUG, "jajaja", ">>NATIVE --ERRORE--");
    }
    else if (SL_RESULT_PARAMETER_INVALID == result) {
        __android_log_print(ANDROID_LOG_DEBUG, "jajaja",
                            ">>NATIVE parameter invalid");
    }
    else if (SL_RESULT_FEATURE_UNSUPPORTED == result) {
        __android_log_print(ANDROID_LOG_DEBUG, "jajaja",
                            ">>NATIVE unsupported");
    }
}
