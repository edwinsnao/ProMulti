//package com.example.king.fragement;
//
//import android.content.Context;
//import android.view.View;
//import android.widget.Toast;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//
//public class ReflectToast
//{
//  private Field field;
//  private Method hideMethod;
//  Context mContext;
//  private Toast mToast;
//  private Object obj;
//  private Method showMethod;
//
//  public ReflectToast(Context paramContext, View paramView)
//  {
//    this.mContext = paramContext;
//    this.mToast = new Toast(this.mContext);
//    this.mToast.setView(paramView);
//    reflectionTN();
//  }
//
//  private void reflectionTN()
//  {
//    try
//    {
//      this.field = this.mToast.getClass().getDeclaredField("mTN");
//      this.field.setAccessible(true);
//      this.obj = this.field.get(this.mToast);
//      this.showMethod = this.obj.getClass().getDeclaredMethod("show", null);
//      this.hideMethod = this.obj.getClass().getDeclaredMethod("hide", null);
//      return;
//    }
//    catch (Exception localException)
//    {
//      localException.printStackTrace();
//    }
//  }
//
//  public void cancel()
//  {
//    try
//    {
//      this.hideMethod.invoke(this.obj, null);
//      return;
//    }
//    catch (Exception localException)
//    {
//      localException.printStackTrace();
//    }
//  }
//
//  public void show()
//  {
//    try
//    {
//      this.showMethod.invoke(this.obj, null);
//      return;
//    }
//    catch (Exception localException)
//    {
//      localException.printStackTrace();
//    }
//  }
//}
//
//
///* Location:              D:\迅雷下载\dex2jar-2.0\classes-dex2jar.jar!\com\example\king\fragement\ReflectToast.class
// * Java compiler version: 6 (50.0)
// * JD-Core Version:       0.7.1
// */