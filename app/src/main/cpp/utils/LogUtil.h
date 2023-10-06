//
// Created by 愛滅 on 2023/9/18.
//

#ifndef VEGSP_V_1_LOGUTIL_H
#define VEGSP_V_1_LOGUTIL_H

#ifdef __cplusplus
extern "C"{
#endif

#include "android/log.h"

#define LOG "NDK-GL-LOG"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG, __VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN, LOG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG, __VA_ARGS__)
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL, LOG, __VA_ARGS__)

#ifdef __cplusplus
}
#endif

#endif //VEGSP_V_1_LOGUTIL_H
