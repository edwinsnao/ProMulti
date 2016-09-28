package com.example.king.fragement;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.king.fragement.main.LogWrap;

import java.lang.reflect.Method;
import java.util.List;

public class OsLogin
  extends AppCompatActivity
{
  Button cancel;
  Button login;
  private List<ApplicationInfo> allAppList;
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.login);
    Toolbar bar = (Toolbar) findViewById(R.id.toolbar);
// wrong
    setSupportActionBar(bar);
        /*
        * 标题栏解释
        * */
    bar.setTitle("Login");
        /*
        * 左上方返回到主界面
        * */
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
    TextView tv = (TextView) findViewById(R.id.tv);

    PackageManager pm = getApplicationContext().getPackageManager();

    allAppList = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
    pm.getInstalledPackages(0);
    ApplicationInfo a = getApplicationInfo("com.android.settings");
    ApplicationInfo b = getApplicationInfo("com.example.king.fragement");
    LogWrap.d(String.valueOf(a));
    LogWrap.d(String.valueOf(b));
    int tmp = compare(a,b);
    LogWrap.d( String.valueOf(tmp));
    tv.setText(String.valueOf(tmp));
    this.login = ((Button)findViewById(R.id.Login));
    this.cancel = ((Button)findViewById(R.id.cancel));
    this.login.setOnClickListener(new OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
//        Intent it  = new Intent(OsLogin.this, OsMain.class);
//        OsLogin.this.startActivity(it);
      }
    });
  }
  public ApplicationInfo getApplicationInfo(String appName) {

    if (appName == null) {

      return null;

    }

    for (ApplicationInfo appinfo : allAppList) {

      if (appName.equals(appinfo.processName)) {
        LogWrap.d(String.valueOf(appinfo));
        return appinfo;

      }

    }

    return null;

  }
  public final int compare(ApplicationInfo a, ApplicationInfo b) {
    PackageManager pm  = getPackageManager();
    Intent it = new Intent();
    it.setComponent(ComponentName.unflattenFromString("com.android.settings"));
    ComponentName aName = it.getComponent();
//    String aName = "com.android.settings";
    Intent it1 = new Intent();
    it1.setComponent(ComponentName.unflattenFromString("com.example.king.fragement"));
//    CharSequence bName = b.loadLabel(pm).toString();
    ComponentName bName = it1.getComponent();
//    String bName = "com.example.king.fragement";
//    ComponentName bName = "com.example.king.fragement";
    int aLaunchCount,bLaunchCount;
    long aUseTime,bUseTime;
    int result = 0;

    try {

      //获得ServiceManager类
      Class<?> ServiceManager = Class
              .forName("android.os.ServiceManager");

      //获得ServiceManager的getService方法
      Method getService = ServiceManager.getMethod("getService", java.lang.String.class);

      //调用getService获取RemoteService
      Object oRemoteService = getService.invoke(null, "usagestats");

      //获得IUsageStats.Stub类
      Class<?> cStub = Class
              .forName("com.android.internal.app.IUsageStats$Stub");
      //获得asInterface方法
      Method asInterface = cStub.getMethod("asInterface", android.os.IBinder.class);
      //调用asInterface方法获取IUsageStats对象
      Object oIUsageStats = asInterface.invoke(null, oRemoteService);
      //获得getPkgUsageStats(ComponentName)方法
      Method getPkgUsageStats = oIUsageStats.getClass().getMethod("getPkgUsageStats", ComponentName.class);
      //调用getPkgUsageStats 获取PkgUsageStats对象
      Object aStats = getPkgUsageStats.invoke(oIUsageStats, aName);
      Object bStats = getPkgUsageStats.invoke(oIUsageStats, bName);
      LogWrap.d(String.valueOf(aStats));
      LogWrap.d(String.valueOf(bStats));

      //获得PkgUsageStats类
      Class<?> PkgUsageStats = Class.forName("com.android.internal.os.PkgUsageStats");

      aLaunchCount = PkgUsageStats.getDeclaredField("launchCount").getInt(aStats);
      bLaunchCount = PkgUsageStats.getDeclaredField("launchCount").getInt(bStats);
      aUseTime = PkgUsageStats.getDeclaredField("usageTime").getLong(aStats);
      bUseTime = PkgUsageStats.getDeclaredField("usageTime").getLong(bStats);

      if((aLaunchCount>bLaunchCount)||
              ((aLaunchCount == bLaunchCount)&&(aUseTime>bUseTime)))
        result = 1;
      else if((aLaunchCount<bLaunchCount)||((aLaunchCount ==
              bLaunchCount)&&(aUseTime<bUseTime)))
        result = -1;
      else {
        result = 0;
      }

    } catch (Exception e) {
      LogWrap.e(e.toString(), e);
    }

    return result;
  }
}


/* Location:              D:\迅雷下载\dex2jar-2.0\classes-dex2jar.jar!\com\example\king\fragement\OsLogin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */