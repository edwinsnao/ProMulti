package com.example.king.fragement;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.king.fragement.jni.Example;


public class JniTest extends AppCompatActivity {
    String name = "Test";

    static {
        System.loadLibrary("JniTest");
    }

  public native String getName();

    public native String getStringFromNative();

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.jni_test);
//        Point p = new Point();
//        Unix.drawByPointer(p);
//        Unix.drawByReference(p);
//        Unix.drawByValue(p);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("JniTest");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText(stringFromJNI()
                +"UID:"+ Example.getuid());
        tv.append("\n" + getStringFromNative());
        tv.append("\ntime=:"+ Example.getTime());
        tv.append("\n5!=:"+ Example.fact(5));
//    tv.append("\n" + getName());
    }

    /*
    *  javah -d ~/AndroidStudioProject/ProMulti/app/src/main/jni -classpath ~/down/android-sdk-linux/platforms/android-21/android.jar; com.example.king.fragement.JniTest
        我是过这个不行，之前可以的
        现在使用test@test-ThinkPad-Edge-E431:~/AndroidStudioProject/ProMulti/app/src/main$
         javah -d jni -classpath java/ com.example.king.fragement.JniTest

    * */
    public native String  stringFromJNI();

    /* This is another native method declaration that is *not*
     * implemented by 'hello-jni'. This is simply to show that
     * you can declare as many native methods in your Java code
     * as you want, their implementation is searched in the
     * currently loaded native libraries only the first time
     * you call them.
     *
     * Trying to call this function will result in a
     * java.lang.UnsatisfiedLinkError exception !
     */
//    public native String  unimplementedStringFromJNI();

    /* this is used to load the 'hello-jni' library on application
     * startup. The library has already been unpacked into
     * /data/data/com.example.hellojni/lib/libhello-jni.so at
     * installation time by the package manager.
     */
//    static {
//        System.loadLibrary("hello-jni");
//    }
}


/* Location:              D:\迅雷下载\dex2jar-2.0\classes-dex2jar.jar!\com\example\king\fragement\JniTest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */

/*
* Error:(14, 0) Error: NDK integration is deprecated in the current plugin.  Consider trying the new experimental plugin.  For details, see http://tools.android.com/tech-docs/new-build-system/gradle-experimental.  Set "android.useDeprecatedNdk=true" in gradle.properties to continue using the current NDK integration.
<a href="openFile:/home/test/AndroidStudioProject/ProMulti/app/build.gradle">Open File</a>
* 需要在gradle.properties中加上android.useDeprecatedNdk=true
* */