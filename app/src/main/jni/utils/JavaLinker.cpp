//
// Created by stefano on 5/25/2016.
//

#include <stddef.h>
#include "JavaLinker.h"

JavaLinker::JavaLinker(JavaVM *jvm, JNIEnv *env, string classSignature) : _callbacks() {

    _jvm = jvm;
    _env = env;

    jclass nativeReceiver = _env->FindClass(classSignature.c_str());

    if(nativeReceiver != NULL) {
        _globalJavaReceiver = reinterpret_cast<jclass>(_env->NewGlobalRef(nativeReceiver));
    }
}

JavaLinker::~JavaLinker() { }

JavaLinker* JavaLinker::getInstance() {
    if(_mInstance != NULL) {
        return  _mInstance;
    } else {
        return NULL;
    }
}

JavaLinker* JavaLinker::getInstance(JavaVM *jvm, JNIEnv *env, string classSignature) {

    if(_mInstance == NULL) {
        _mInstance = new JavaLinker(jvm, env, classSignature);
    }

    return _mInstance;
}

void JavaLinker::link(string methodName, string methodSignature) {

    jmethodID method = _env->GetStaticMethodID(_globalJavaReceiver, methodName.c_str(), methodSignature.c_str());

    if(method != NULL) {

        _callbacks.insert( std::pair<string, jmethodID>(methodName, method));
    }

    Logger::log("Linked method " + methodName);

    //Global references must be explicitly disposed of by calling DeleteGlobalRef()

    /*map<string,string>::iterator it;
    for(it = signatures.begin(); it != signatures.end(); it++) {

        const char *name = (it->first).c_str();
        const char *signature = (it->second).c_str();*/
}

void JavaLinker::invoceCallback(string methodName, void *params) {

    JNIEnv* env;

    int envStatus = _jvm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6);
    bool mustDetach = false;

    if(envStatus == JNI_EDETACHED) {

        int result = _jvm->AttachCurrentThread(&env, NULL);
        if(result == JNI_OK ) {
            mustDetach = true;
        }
    } else if (envStatus == JNI_OK) {
        // VM is already attached
    } else if (envStatus == JNI_EVERSION) {
        // Version not supported
    }

    map<string, jmethodID>::const_iterator pos = _callbacks.find(methodName.c_str());
    if (pos == _callbacks.end()) {
        //handle the error
    } else {

        jmethodID method = pos->second;
        if(method != NULL) {
            if(methodName == "sampleLoaded") {
                env->CallStaticVoidMethod(_globalJavaReceiver, method, reinterpret_cast<int>(params));
            }
        }
    }

    if(mustDetach) {
        _jvm->DetachCurrentThread();
    }
}
