//package com.example.king.fragement;
//
//import android.app.AlertDialog;
//import android.app.AlertDialog.Builder;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.king.fragement.main.LogWrap;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//
//public class BroadcastTest
//        extends AppCompatActivity {
//    int i = 100;
//    boolean isShown = false;
//    Context mContext;
//    ReflectToast toast;
//    MyToast toast1;
//
//    protected void onCreate(Bundle paramBundle) {
//        super.onCreate(paramBundle);
////    setContentView(2130968608);
//        setContentView(R.layout.broadcasr);
////    paramBundle = (Button)findViewById(2131689647);
//        Button btn = (Button) findViewById(R.id.btn);
//        Button btn1 = (Button) findViewById(R.id.btn1);
//        Button btn2 = (Button) findViewById(R.id.btn2);
//        Button btn3 = (Button) findViewById(R.id.btn3);
//        Button btn4 = (Button) findViewById(R.id.btn4);
//        Button btn5 = (Button) findViewById(R.id.btn5);
////    Button localButton1 = (Button)findViewById(2131689648);
////    Button localButton2 = (Button)findViewById(2131689649);
////    Button localButton3 = (Button)findViewById(2131689650);
////    Button localButton4 = (Button)findViewById(2131689651);
////    Button localButton5 = (Button)findViewById(2131689652);
//        final TextView localTextView = new TextView(this);
//        localTextView.setText("hello");
//        Toolbar bar = (Toolbar) findViewById(R.id.toolbar);
//// wrong
//        setSupportActionBar(bar);
//        /*
//        * 标题栏解释
//        * */
//        bar.setTitle("Broadcast");
//        /*
//        * 左上方返回到主界面
//        * */
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
////    paramBundle.setOnClickListener(new OnClickListener()
//        btn.setOnClickListener(new OnClickListener() {
//            public void onClick(View paramAnonymousView) {
////        paramAnonymousView = new Intent();
////        paramAnonymousView.setAction("broadcasrTest");
////        paramAnonymousView.putExtra("msg", "hello");
////        BroadcastTest.this.sendBroadcast(paramAnonymousView);
//                Intent it = new Intent();
//                it.setAction("broadcasrTest");
//                it.putExtra("msg", "hello");
//                BroadcastTest.this.sendBroadcast(it);
//            }
//        });
//        btn1.setOnClickListener(new OnClickListener() {
//            public void onClick(View v) {
//                BroadcastTest.this.toast1.hide();
//            }
//        });
//        btn2.setOnClickListener(new OnClickListener() {
//            public void onClick(View paramAnonymousView) {
//                BroadcastTest.this.toast = new ReflectToast(BroadcastTest.this, localTextView);
//                if (BroadcastTest.this.isShown) {
//                    BroadcastTest.this.toast.cancel();
//                    BroadcastTest.this.isShown = false;
//                    return;
//                }
//                BroadcastTest.this.toast.show();
//                BroadcastTest.this.isShown = true;
//            }
//        });
//        btn3.setOnClickListener(new OnClickListener() {
//            public void onClick(View paramAnonymousView) {
//                AlertDialog dialog = new Builder(BroadcastTest.this).setMessage("hello").setNegativeButton("cancel", null).setNeutralButton("neutral", null).setPositiveButton("ok", null).create();
//                Window v = dialog.getWindow();
//                v.setGravity(48);
//                WindowManager.LayoutParams localLayoutParams = v.getAttributes();
//                localLayoutParams.alpha = 0.5F;
//                v.setAttributes(localLayoutParams);
//                try {
//                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
//                    field.setAccessible(true);
//                    field.set(dialog, Boolean.valueOf(false));
//                    dialog.show();
////          return;
////          localObject = paramAnonymousView.getClass().getSuperclass().getDeclaredField("mShowing");
////          ((Field)localObject).setAccessible(true);
////          ((Field)localObject).set(paramAnonymousView, Boolean.valueOf(false));
////          paramAnonymousView.show();
////          return;
//                } catch (NoSuchFieldException localNoSuchFieldException) {
//                    for (; ; ) {
//                        localNoSuchFieldException.printStackTrace();
//                    }
//                } catch (IllegalAccessException localIllegalAccessException) {
//                    for (; ; ) {
//                        localIllegalAccessException.printStackTrace();
//                    }
//                }
//            }
//        });
//        btn4.setOnClickListener(new OnClickListener() {
//            public void onClick(View paramAnonymousView) {
//                if (BroadcastTest.this.i > 0) {
////          BroadcastTest.this.toast1 = new MyToast(BroadcastTest.this, BroadcastTest.this, "reflect");
//                    BroadcastTest.this.toast1 = new MyToast(BroadcastTest.this, "reflect");
//                    new Thread(new Runnable() {
//                        public void run() {
//                            BroadcastTest.this.toast1.show();
//                        }
//                    }).start();
//                    try {
////                        Thread.sleep(100L);
//                        Thread.sleep(100);
//                        BroadcastTest.this.toast1.hide();
////            return;
//                    } catch (InterruptedException e) {
//                        for (; ; ) {
//                            e.printStackTrace();
//                        }
//                    }
//                } else {
//                    Toast.makeText(BroadcastTest.this, "你已经是开发者了", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//        btn5.setOnClickListener(new OnClickListener() {
//            public void onClick(View paramAnonymousView) {
////        paramAnonymousView = new Intent();
////        paramAnonymousView.setAction("broadcasrTestSticky");
////        paramAnonymousView.putExtra("msg", "helloSticky");
////        BroadcastTest.this.mContext.sendStickyBroadcast(paramAnonymousView);
//                Intent it = new Intent();
//                it.setAction("broadcasrTestSticky");
//                it.putExtra("msg", "helloSticky");
//                BroadcastTest.this.mContext.sendStickyBroadcast(it);
//            }
//        });
//    }
//
//    class MyToast {
//        Context context = null;
//        Object obj = null;
//
//        public MyToast(Context paramContext, String paramString) {
//            this.context = paramContext;
////      this$1 = Toast.makeText(paramContext, paramString, 0);
//            Toast toast = Toast.makeText(paramContext, "Test", Toast.LENGTH_SHORT);
//            try {
////        paramContext = BroadcastTest.this.getClass().getDeclaredField("mTN");
////        paramContext.setAccessible(true);
//                Field field = toast.getClass().getDeclaredField("mTN");
//                field.setAccessible(true);
////        this.obj = paramContext.get(BroadcastTest.this);
//                obj = field.get(toast);
////        return;
//            } catch (Exception this$1) {
//                LogWrap.d( BroadcastTest.this.toString());
//            }
//        }
//
//        public void hide() {
//            try {
////        Method method = obj.getClass().getDeclaredMethod("hide",null);
////        method.invoke(obj,null);
//                this.obj.getClass().getDeclaredMethod("hide",
//                        null).invoke(this.obj, null);
////        return;
//            } catch (Exception localException) {
//                LogWrap.d(localException.toString());
//                localException.printStackTrace();
//            }
//        }
//
//        public void show() {
//            try {
//                Field localField = this.obj.getClass().getDeclaredField("mNextView");
//                localField.setAccessible(true);
//                View localView = ((LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.toast, null);
//                /*
//                * 这里不能用tv2因为tv2是broadcastxml中的tv而我们现在是要用toat的tv所以使用tv1
//                * 否则会报nullpointer
//                * 因为我现在使用inflater
//                * 布局文件为toast
//                * toast里面没有tv2
//                * */
////                TextView localTextView = (TextView) localView.findViewById(R.id.tv2);
//                TextView localTextView = (TextView) localView.findViewById(R.id.tv1);
////                Button localTextView = (Button) localView.findViewById(R.id.tv1);
////        localField.set(obj,localTextView);
//                StringBuilder localStringBuilder = new StringBuilder().append("还有");
////        BroadcastTest localBroadcastTest = BroadcastTest.this;
////        int i = localBroadcastTest.i;
////        localBroadcastTest.i = (i - 1);
//                i--;
//                localTextView.setText("还有" + i+"步成为开发者");
//                this.obj.getClass().getDeclaredMethod("show", null).invoke(this.obj, null);
//                localField.set(this.obj, localView);
//                Method method = obj.getClass().getDeclaredMethod("show", null);
//                method.invoke(obj, null);
////        localField.set(this.obj, localView);
////        this.obj.getClass().getDeclaredMethod("show", null).invoke(this.obj, null);
////        return;
//            } catch (Exception localException) {
//                LogWrap.d(localException.toString());
//                localException.printStackTrace();
//            }
//        }
//    }
//}
//
//
///* Location:              D:\迅雷下载\dex2jar-2.0\classes-dex2jar.jar!\com\example\king\fragement\BroadcastTest.class
// * Java compiler version: 6 (50.0)
// * JD-Core Version:       0.7.1
// */