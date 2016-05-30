//
// Created by stefano on 5/25/2016.
//

#ifndef NATIVEMUSICSAMPLER_JAVALINKER_H
#define NATIVEMUSICSAMPLER_JAVALINKER_H

#include <jni.h>
#include <string>
#include <map>
#include <vector>
#include "Logger.h"

using namespace std;

class JavaLinker {

public:
    static JavaLinker *getInstance();
    static JavaLinker *getInstance(JavaVM *jvm, JNIEnv *env, string classSignature);
    void link(string methodName, string methodSignature);
    void invoceCallback(string methodName, void *params);

private:
    JavaLinker(JavaVM *jvm, JNIEnv *env, string classSignature);
    ~JavaLinker();
    JavaVM *_jvm;
    JNIEnv *_env;
    jclass _globalJavaReceiver;
    map<string, jmethodID> _callbacks;
};

static JavaLinker *_mInstance;

#endif //NATIVEMUSICSAMPLER_JAVALINKER_H
