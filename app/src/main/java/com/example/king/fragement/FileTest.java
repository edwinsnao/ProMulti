package com.example.king.fragement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

public class FileTest
  extends AppCompatActivity
{
  final String FILE_NAME = "file_test";
  
  /* Error */
  private String read() throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: ldc 16
    //   3: invokevirtual 39	com/example/king/fragement/FileTest:openFileInput	(Ljava/lang/String;)Ljava/io/FileInputStream;
    //   6: astore_1
    //   7: sipush 1024
    //   10: newarray <illegal type>
    //   12: astore_2
    //   13: new 41	java/lang/StringBuilder
    //   16: dup
    //   17: ldc 43
    //   19: invokespecial 45	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   22: astore_3
    //   23: aload_1
    //   24: aload_2
    //   25: invokevirtual 50	java/io/FileInputStream:read	([B)I
    //   28: istore 4
    //   30: iload 4
    //   32: ifle +29 -> 61
    //   35: aload_3
    //   36: new 52	java/lang/String
    //   39: dup
    //   40: aload_2
    //   41: iconst_0
    //   42: iload 4
    //   44: invokespecial 55	java/lang/String:<init>	([BII)V
    //   47: invokevirtual 59	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   50: pop
    //   51: goto -28 -> 23
    //   54: astore_1
    //   55: aload_1
    //   56: invokevirtual 62	java/io/FileNotFoundException:printStackTrace	()V
    //   59: aconst_null
    //   60: areturn
    //   61: aload_1
    //   62: invokevirtual 65	java/io/FileInputStream:close	()V
    //   65: aload_3
    //   66: invokevirtual 68	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   69: astore_1
    //   70: aload_1
    //   71: areturn
    //   72: astore_1
    //   73: aload_1
    //   74: invokevirtual 69	java/io/IOException:printStackTrace	()V
    //   77: goto -18 -> 59
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	80	0	this	FileTest
    //   6	18	1	localFileInputStream	java.io.FileInputStream
    //   54	8	1	localFileNotFoundException	FileNotFoundException
    //   69	2	1	str	String
    //   72	2	1	localIOException	java.io.IOException
    //   12	29	2	arrayOfByte	byte[]
    //   22	44	3	localStringBuilder	StringBuilder
    //   28	15	4	i	int
    // Exception table:
    //   from	to	target	type
    //   0	23	54	java/io/FileNotFoundException
    //   23	30	54	java/io/FileNotFoundException
    //   35	51	54	java/io/FileNotFoundException
    //   61	70	54	java/io/FileNotFoundException
    //   0	23	72	java/io/IOException
    //   23	30	72	java/io/IOException
    //   35	51	72	java/io/IOException
    //   61	70	72	java/io/IOException
//    InputStream inputStream =
    FileInputStream localFileInputStream = openFileInput("file_test");
//    byte[] arrayOfByte = new byte['Ѐ'];
    byte[] arrayOfByte = new byte[1024];
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
  {
    try
    {
      PrintStream localPrintStream = new PrintStream(openFileOutput("file_test", 32768));
      localPrintStream.println(paramString);
      localPrintStream.close();
      return;
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.file);
    Toolbar bar = (Toolbar) findViewById(R.id.toolbar);
// wrong
    setSupportActionBar(bar);
        /*
        * 标题栏解释
        * */
    bar.setTitle("FileTest");
        /*
        * 左上方返回到主界面
        * */
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
//    System.out.println(new StringBuilder("a").append("b").append("c"));
    Button read = (Button)findViewById(R.id.read);
    Button write = (Button)findViewById(R.id.write);
    final EditText localEditText1 = (EditText)findViewById(R.id.et1);
    final EditText localEditText2 = (EditText)findViewById(R.id.et2);
    write.setOnClickListener(new OnClickListener() {
      public void onClick(View paramAnonymousView) {
        FileTest.this.write(localEditText1.getText().toString());
        localEditText1.setText("");
      }
    });
    read.setOnClickListener(new OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        try {
          localEditText2.setText(FileTest.this.read());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
  }
}


/* Location:              D:\迅雷下载\dex2jar-2.0\classes-dex2jar.jar!\com\example\king\fragement\FileTest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */