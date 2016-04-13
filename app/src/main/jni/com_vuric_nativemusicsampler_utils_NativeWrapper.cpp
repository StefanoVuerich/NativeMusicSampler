#include "com_vuric_nativemusicsampler_utils_NativeWrapper.h"
#include <cstddef>

#include "sampler/Options.h"
#include "sampler/Console.h"

jclass globalJavaReceiver;
jmethodID updatePlayerStatus;
jmethodID sendWaveData;
IConsole *console;
IPlayersController *playersController;

JNIEXPORT jboolean JNICALL Java_com_vuric_nativemusicsampler_utils_NativeWrapper_init
        (JNIEnv *env, jobject obj, jint numberOfSlots, jintArray bands) {

    jclass nativeReceiver = env->FindClass("com/vuric/nativesampler/nativeaudio/NativeReceiver");

    if (nativeReceiver != NULL) {

        globalJavaReceiver = (jclass)env->NewGlobalRef(nativeReceiver);

        if (globalJavaReceiver != NULL) {

            updatePlayerStatus = env->GetMethodID(globalJavaReceiver, "updatePlayerStatus", "(I)V");
            sendWaveData = env->GetMethodID(globalJavaReceiver,"triggerSoundWaveDrawn", "([S)V");
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

    return true;
}

JNIEXPORT jboolean JNICALL Java_com_vuric_nativemusicsampler_utils_NativeWrapper_loadSample
        (JNIEnv *end, jobject obj, jint playerIndex, jstring path) {

}