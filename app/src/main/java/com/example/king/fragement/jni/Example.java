/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.example.king.fragement.jni;

public class Example {
  public static long getuid() {
    return ExampleJNI.getuid();
  }

  public static void setPI(double value) {
    ExampleJNI.PI_set(value);
  }

  public static double getPI() {
    return ExampleJNI.PI_get();
  }

  public static int fact(int n) {
    return ExampleJNI.fact(n);
  }

  public static int mod(int x, int y) {
    return ExampleJNI.mod(x, y);
  }

  public static String getTime() {
    return ExampleJNI.getTime();
  }

  public static String toUpperCase(String str) {
    return ExampleJNI.toUpperCase(str);
  }

}
