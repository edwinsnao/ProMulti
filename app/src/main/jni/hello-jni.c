/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
#include <string.h>
#include <jni.h>
/*#include <prof.h>*/
#include <math.h>
/*#include "prof.h"*/

#ifdef  MY_ANDROID_NDK_PROFILER_ENABLED
#include<prof.h>
#endif

/* This is a trivial JNI example where we use a native method
 * to return a new VM String. See the corresponding Java source
 * file located at:
 *
 *   apps/samples/hello-jni/project/src/com/example/hellojni/HelloJni.java
 */
jstring
Java_com_example_king_fragement_JniTest_stringFromJNI(JNIEnv *env,
                                                      jobject thiz) {
    int i;

#if defined(__arm__)
#if defined(__ARM_ARCH_7A__)
    #if defined(__ARM_NEON__)
      #define ABI "armeabi-v7a/NEON"
    #else
      #define ABI "armeabi-v7a"
    #endif
#else
#define ABI "armeabi"
#endif
#elif defined(__i386__)
    #define ABI "x86"
 #elif defined(__mips__)
    #define ABI "mips"
 #else
    #define ABI "unknown"
#endif
#ifdef  MY_ANDROID_NDK_PROFILER_ENABLED
    monstartup("libJniTest.so");
#endif
    test();
#ifdef  MY_ANDROID_NDK_PROFILER_ENABLED
    moncleanup();
#endif
    return (*env)->NewStringUTF(env, "Hello from JNI !  Compiled with ABI " ABI ".");
}

jint add() {
    int z = 0;
    int i = 10000, j = 0;
    for (i = 0; i < 10000; i++) {
        for (j = 0; j < 10000; j++) {
            z = i * j;
        }
    }
    return i + j;
}

jint test() {
    add();
//  int i;
//  char buffer[] = "Hello world/n";
//  printf("Buffer before memset: %s/n", buffer);
//  memset(buffer, '*', strlen(buffer) );
//  printf("Buffer after memset: %s/n", buffer);
//
//  FILE *fp = fopen("/sdcard/a.txt","at");
//
//  fprintf(fp,"%s\n",buffer);
//
//  for(i=0;i<100000;i++)
//  {
//      char ch[30];
//      sprintf(ch,"the number is %d\n",i);
//      char str[30];
//      memcpy(str,ch,strlen(ch));
//      fputs(ch,fp);
////        fprintf(fp,"%s\n",ch);
//  }
//
//  fclose(fp);

    int i, j;
    double s = 0, temp = 1.0;
    for (i = 1; i <= 5000000; i++) {
        s += sqrt(temp);
        temp = s;
    }

    return 0;
}