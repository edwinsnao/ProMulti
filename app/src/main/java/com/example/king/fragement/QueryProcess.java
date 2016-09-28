package com.example.king.fragement;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class QueryProcess
  extends AppCompatActivity
{
  private Button mButton = null;
  private String mText = "";
  private TextView mTextView = null;
  private TextView mTime = null;
  
  private void updateProcessInfo()
  {
    this.mText = "";
    this.mTextView.setText(this.mText);
    Object localObject = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
    updateTimeInfo();
    localObject = ((ActivityManager)localObject).getRunningAppProcesses().iterator();
    while (((Iterator)localObject).hasNext())
    {
      String str = ((RunningAppProcessInfo)((Iterator)localObject).next()).processName;
      this.mText = this.mTextView.getText().toString();
      this.mText = (this.mText + str + "\n\n");
      this.mTextView.setText(this.mText);
    }
  }
  
  private void updateTimeInfo()
  {
    String str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//    System.out.println(str);
    this.mTime.setText(str);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.process);
    Toolbar bar = (Toolbar) findViewById(R.id.toolbar);
// wrong
    setSupportActionBar(bar);
        /*
        * 标题栏解释
        * */
    bar.setTitle("QueryProcess");
        /*
        * 左上方返回到主界面
        * */
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
    this.mTextView = ((TextView)findViewById(R.id.text));
    this.mTime = ((TextView)findViewById(R.id.time));
    this.mButton = ((Button)findViewById(R.id.updateBtn));
    this.mButton.setOnClickListener(new OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        QueryProcess.this.updateProcessInfo();
      }
    });
  }
}


/* Location:              D:\迅雷下载\dex2jar-2.0\classes-dex2jar.jar!\com\example\king\fragement\QueryProcess.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */