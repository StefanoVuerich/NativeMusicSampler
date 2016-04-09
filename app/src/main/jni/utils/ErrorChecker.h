//
// Created by stefano on 12/15/2015.
//

#ifndef JNIEXAMPLE_ERRORCHECKER_H
#define JNIEXAMPLE_ERRORCHECKER_H

#include <SLES/OpenSLES_Android.h>
#include <android/log.h>

class ErrorChecker {

public:
    ErrorChecker();
    ~ErrorChecker();
    static void check(SLresult result);
};


#endif //JNIEXAMPLE_ERRORCHECKER_H
