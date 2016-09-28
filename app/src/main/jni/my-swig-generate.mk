
    ifndef MY_SWIG_PACKAGE
       $(error MY_SWIG_PACKAGE is not defined.)
    endif

#可能是因为android-studio的结构和eclipse结构不同所以
#AS：MY_SWIG_OUTDIR:=$(NDK_PROJECT_PATH)/src/main/java/$(subst .,/,$(MY_SWIG_PACKAGE))
#eclipse：MY_SWIG_OUTDIR:=$(NDK_PROJECT_PATH)/src/$(subst .,/,$(MY_SWIG_PACKAGE))
#差别在于没有了main/java
#swig的命令也有点不同
# swig -java -package com.example.king.fragement.jni -outdir src/main/java/com/example/king/fragement/jni src/main/jni/Unix.i
# 这个swig命令是用于生成Unix.java和UnixJni.java
# 而Android.mk和mygernerate中是用于封装wrapc并编译so
# 原来rebuild中已经编译了ndk
# 不过我加入了ndk-build -B也没所谓
    MY_SWIG_OUTDIR:=$(NDK_PROJECT_PATH)/src/main/java/$(subst .,/,$(MY_SWIG_PACKAGE))
    ifndef MY_SWIG_TYPE
        MY_SWIG_TYPE := c
    endif
    #ifeq和后面的语句需要一个空格隔开
    ifeq ($(MY_SWIG_TYPE),cxx)
       MY_SWIG_MODE := -c++
    else
       MY_SWIG_MODE :=
    endif
    LOCAL_CPP_EXTENTION += .cxx
    LOCAL_SRC_FILES+=$(foreach MY_SWIG_INTERFACE,\
          $(MY_SWIG_INTERFACES),\
          $(basename $(MY_SWIG_INTERFACE))_wrap.$(MY_SWIG_TYPE))

    #LOCAL_CPP_EXTENSION += .cxx
    # swig -java -c++ -package com.example.king.fragement.jni -outdir ../java/com/example/king/fragement/jni Unix.i
    #c++
    /*%_warp.$(MY_SWIG_TYPE) : %.i \
       $(call host-mkdir,$(MY_SWIG_OUTDIR)) \
       swig -java -c++ \
       $(MY_SWIG_MODE)  \
       -package $(MY-SWIG_PACKAGE)  \
       -outdir $(MY_SWIG_OUTDIR)   \
       $<*/
    #c
    %_warp.$(MY_SWIG_TYPE) : %.i \
       $(call host-mkdir,$(MY_SWIG_OUTDIR)) \
       swig -java  \
       $(MY_SWIG_MODE)  \
       -package $(MY-SWIG_PACKAGE)  \
       -outdir $(MY_SWIG_OUTDIR)   \
       $<