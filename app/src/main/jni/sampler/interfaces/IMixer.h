//
// Created by stefano on 3/16/2016.
//

#ifndef JNIEXAMPLE_IMIXER_H
#define JNIEXAMPLE_IMIXER_H

#include <SLES/OpenSLES.h>

class IMixer {

public:
    virtual SLObjectItf *getMixer() = 0;
};

#endif //JNIEXAMPLE_IMIXER_H
