LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := JniTest
LOCAL_SRC_FILES := hello-jni.c
LOCAL_SRC_FILES += $(TARGET_ARCH_ABI)/libandprof.a
#启用android ndk profiler
MY_ANDROID_NDK_PROFILER_ENABLED :=true

ifeq ( $(MY_ANDROID_NDK_PROFILER_ENABLED),true)

 $(info GNU Profiler is enabled)

#启用监控功能
LOCAL_CFLAGS += -DMY_ANDROID_NDK_PROFILER_ENABLED
#LOCAL_CFLAGS += -pg
#LOCAL_CFLAGS := -pg

#使用Enabled the monitor function 静态库
LOCAL_STATIC_LIBRARIES := andprof
#LOCAL_STATIC_LIBRARIES := android-ndk-profiler
endif




MY_SWIG_PACKAGE := com.example.king.fragement.jni
MY_SWIG_INTERFACES := Unix.i
MY_SWIG_TYPE := c

include $(LOCAL_PATH)/my-swig-generate.mk

include $(BUILD_SHARED_LIBRARY)

#如果profiler被启用则导入android ndk profiler库模块
ifeq ( $(MY_ANDROID_NDK_PROFILER_ENABLED),true)
 $(call import-module ,android-ndk-profiler/jni)
endif

#“$”前面一定要有一个空格
#endif而不是Endif
#1. 这个工具只能一次profile一个动态库
# 2. 为了分析gmon.out，gprof需要使用obj下面的so，而不是用release版本的so，会报no symbol的错误
# 3. 确保moncleanup()被调用到，否则不会有gmon.out产生