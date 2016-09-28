%module Unix

%{
#include <unistd.h>
%}
/*
struct Point{
    int x;
    int y;
};
*/


# swig -java -package com.example.king.fragement.jni -outdir ../java/com/example/king/fragement/jni Unix.i

typedef unsigned int uid_t;

extern uid_t getuid(void);

#不要漏掉后面的分号

#%javaconst(1); 直接表示原始数据
%javaconst(0);
%constant int MAX_HEIGHT = 320;
#%javaconst(0);  相当于使用了%constant指令定义
#%javaconst(0);
##define MAX_WIDTH 640
#extern int counter;

#%immutable;
#extern int readOnly;

#%mutable;
#extern int readWrite;
#%javaconst(1);  没有了get
#public final static int ONE = 1;
#  public final static int TWO = 2;
#  public final static int THREE = TWO + 1;
#  public final static int FOUR = THREE + 1;
#%javaconst(0);
#public final static int ONE = UnixJNI.ONE_get();
#  public final static int TWO = UnixJNI.TWO_get();
#  public final static int THREE = UnixJNI.THREE_get();
#  public final static int FOUR = UnixJNI.FOUR_get();
#enum {ONE = 1 , TWO = 2, THREE , FOUR};
#%include "enumtypeunsafe.swg"
#%include "enums.swg"
#enum Numbers {ONE = 1 , TWO = 2, THREE , FOUR};
/*#%include "enumtypeunsafe.swg" 类型不安全与enum {ONE = 1 , TWO = 2, THREE , FOUR};效果一样
而且enum 和 enum Numbers不能同时存在
*/

#void drawByPointer(struct Point* p);
#void drawByReference(struct Point& p);
#void drawByValue(struct Point p);

#void func(int a = 1, int b = 2, int c = 3);
/*
%{
class AsyncUidProvider{
public:
    AsyncUidProvider(){

    }
    virtual ~ AsyncUidProvider(){
    }
    void get(){
        onUid(getuid());
    }
    virtual void onUid(uid_t uid){

    }
    };
%}

class AsyncUidProvider{
public:
    AsyncUidProvider();
    virtual ~ AsyncUidProvider();

    void get();
    virtual void onUid(uid_t uid);


};
*/