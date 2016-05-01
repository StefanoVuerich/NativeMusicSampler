#include "com_vuric_nativemusicsampler_nativeaudio_NativeWrapper.h"
#include <cstddef>

#include "sampler/interfaces/IConsole.h"

#include "sampler/Options.h"
#include "utils/Logger.h"
#include "sampler/Console.h"

jclass globalJavaReceiver;
jmethodID sampleLoaded;
jmethodID sendWaveData;
IConsole *console;

JNIEXPORT void JNICALL Java_com_vuric_nativemusicsampler_nativeaudio_NativeWrapper_init
(JNIEnv *env, jclass clazz, jint numberOfSlots, jintArray bands) {

    jclass nativeReceiver = env->FindClass("com/vuric/nativemusicsampler/nativeaudio/NativeReceiver");

    if (nativeReceiver != NULL) {

        globalJavaReceiver = (jclass)env->NewGlobalRef(nativeReceiver);

        if (globalJavaReceiver != NULL) {

            sampleLoaded = env->GetMethodID(globalJavaReceiver, "sampleLoaded", "(I)V");
        }
    }

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

JNIEXPORT void JNICALL Java_com_vuric_nativemusicsampler_nativeaudio_NativeWrapper_loadSample
        (JNIEnv *env , jclass clazz, jint sloIndex, jstring path) {

    const char *cPath = env->GetStringUTFChars(path,NULL);
    console-> getPlayer(sloIndex)->load(cPath);
    env->ReleaseStringUTFChars(path,cPath);
}

JNIEXPORT jint JNICALL Java_com_vuric_nativemusicsampler_nativeaudio_NativeWrapper_setPlayState
        (JNIEnv *env , jclass clazz, jint slotIndex , jint state) {

    console->getPlayer(slotIndex)->play();
}