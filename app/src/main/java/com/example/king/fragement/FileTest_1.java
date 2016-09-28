package com.example.king.fragement;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Timer;
import java.util.TimerTask;

public class FileTest_1
  extends Activity
{

  final String FILE_NAME = "file_test";
  
  private String read()
    throws IOException
  {
    FileInputStream localFileInputStream = openFileInput("file_test");
    byte[] arrayOfByte = new byte['Ѐ'];
    StringBuilder localStringBuilder = new StringBuilder("");
    for (;;)
    {
      int i = localFileInputStream.read(arrayOfByte);
      if (i == -1) {
        break;
      }
      localStringBuilder.append(new String(arrayOfByte, 0, i));
    }
    localFileInputStream.close();
    return localStringBuilder.toString();
  }
  
  private void write(String paramString)
    throws FileNotFoundException
  {
    PrintStream localPrintStream = new PrintStream(openFileOutput("file_test", 32768));
    localPrintStream.println(paramString);
    localPrintStream.close();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.file);
//    System.out.println(new StringBuilder("a").append("b").append("c"));
    Button read = (Button)findViewById(R.id.read);
    Button write = (Button)findViewById(R.id.write);
    final EditText localEditText1 = (EditText)findViewById(R.id.et1);
    final EditText localEditText2 = (EditText)findViewById(R.id.et2);
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        InputMethodManager imm = (InputMethodManager)FileTest_1.this.getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        Toast.makeText(FileTest_1.this, "show", Toast.LENGTH_SHORT).show();
      }
    }, 1000);  //在一秒后打开
    read.setOnClickListener(new OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        try
        {
          localEditText1.setText(FileTest_1.this.read());
//          return;
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
      }
    });
    write.setOnClickListener(new OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        try
        {
          FileTest_1.this.write(localEditText2.getText().toString());
          localEditText2.setText("");
//          return;
        }
        catch (FileNotFoundException e)
        {
          e.printStackTrace();
        }
      }
    });
  }
}


/* Location:              D:\迅雷下载\dex2jar-2.0\classes-dex2jar.jar!\com\example\king\fragement\FileTest_1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */