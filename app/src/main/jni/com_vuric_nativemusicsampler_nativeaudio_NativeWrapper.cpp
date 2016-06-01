#include "com_vuric_nativemusicsampler_nativeaudio_NativeWrapper.h"
#include <cstddef>

#include "sampler/interfaces/IConsole.h"

#include "sampler/Options.h"
#include "utils/Logger.h"
#include "sampler/Console.h"
#include "utils/JavaLinker.h"

static IConsole *console;
static JavaLinker *javaLinker;
static JavaVM *jvm;

jint JNI_OnLoad(JavaVM* vm, void* reserved) {

    jvm = vm;

    JNIEnv* env;
    if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return -1;
    }

    // Get jclass with env->FindClass.
    // Register methods with env->RegisterNatives.

    return JNI_VERSION_1_6;
}

void JNICALL Java_com_vuric_nativemusicsampler_nativeaudio_NativeWrapper_init
(JNIEnv *env, jclass clazz, jint numberOfSlots, jintArray bands) {

    Options *options = new Options();
    options->_playersNumber = numberOfSlots;

    if(bands != NULL) {

        jboolean isCopy = (jboolean)JNI_TRUE;
        jint *body = env->GetIntArrayElements(bands, &isCopy);;
        options->_bandLevels = body;
    }

    console = new Console(*options);

    Logger::log("Init called");
}

jboolean JNICALL Java_com_vuric_nativemusicsampler_nativeaudio_NativeWrapper_loadSample
        (JNIEnv *env , jclass clazz, jint sloIndex, jstring path) {

    const char *cPath = env->GetStringUTFChars(path,NULL);
    jboolean loaded = console-> getPlayer(sloIndex)->load(cPath);
    env->ReleaseStringUTFChars(path,cPath);

    return loaded;
}

jint JNICALL Java_com_vuric_nativemusicsampler_nativeaudio_NativeWrapper_setPlayState
        (JNIEnv *env , jclass clazz, jint slotIndex , jint state) {

    switch(state) {

        case 0:
            console->getPlayer(slotIndex)->stop();
            break;

        case 1:
            console->getPlayer(slotIndex)->play();
            break;

        case 2:
            console->getPlayer(slotIndex)->pause();
            break;
    }

    return state;
}

void JNICALL Java_com_vuric_nativemusicsampler_nativeaudio_NativeWrapper_initLinker
(JNIEnv *env, jclass clazz, jstring className) {

    const char *cName = env->GetStringUTFChars(className,NULL);
    javaLinker = JavaLinker::getInstance(jvm, env, cName);
    env->ReleaseStringUTFChars(className,cName);
}

void JNICALL Java_com_vuric_nativemusicsampler_nativeaudio_NativeWrapper_linkCallbackFunction
(JNIEnv *env, jclass clazz, jstring methodName, jstring methodSignature) {

    if(javaLinker != NULL) {

        const char *mName = env->GetStringUTFChars(methodName,NULL);
        const char *mSignature = env->GetStringUTFChars(methodSignature,NULL);

        javaLinker->link(mName, mSignature);

        env->ReleaseStringUTFChars(methodName,mName);
        env->ReleaseStringUTFChars(methodSignature,mSignature);
    }
}

jboolean JNICALL Java_com_vuric_nativemusicsampler_nativeaudio_NativeWrapper_setVolume
        (JNIEnv *env, jclass clazz, jint selectedSlotID, jint volume) {

    return console->getPlayer(selectedSlotID)->setVolume(volume);
}