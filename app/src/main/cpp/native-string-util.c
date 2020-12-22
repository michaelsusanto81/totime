#include <string.h>
#include <jni.h>

JNIEXPORT jstring JNICALL
Java_id_ac_ui_cs_mobileprogramming_michaelsusanto_totime_util_StringUtil_capitalize(
    JNIEnv* env,
    jobject thiz,
    jstring input
) {
    const char *nativeInput = (*env)->GetStringUTFChars(env, input, 0);
    (*env)->ReleaseStringUTFChars(env, input, nativeInput);

    char output[256];
    strcpy(output, nativeInput);
    if(output[0] >= 'a' && output[0] <= 'z') {
        output[0] += 'A' - 'a';
    }

    return (*env)->NewStringUTF(env, output);
}