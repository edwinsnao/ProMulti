package com.example.king.fragement.main.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ken on 16/5/28.
 */
public class Html2BitmapUtils {
    public static void convert(final WebView webView, String htmlCode, final ImageView image){
        WebSettings settings = webView.getSettings();
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(false);
        settings.setJavaScriptEnabled(true);
        settings.setSupportMultipleWindows(false);
        webView.setDrawingCacheEnabled(true);

        settings.setLoadsImagesAutomatically(true);
        settings.setLightTouchEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        webView.loadData(htmlCode,"text/html;charset=UTF-8",null);

        class Background extends AsyncTask<Void,Void,Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                try{
                    Thread.sleep(2000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                webView.measure(View.MeasureSpec.makeMeasureSpec(
                        View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                webView.layout(0, 0, webView.getMeasuredWidth(),
                        webView.getMeasuredHeight());
                webView.setDrawingCacheEnabled(true);
                webView.buildDrawingCache();
                Bitmap b = Bitmap.createBitmap(webView.getMeasuredWidth(),webView.getMeasuredHeight(), Bitmap.Config.RGB_565);
                Canvas canvas = new Canvas(b);
                Paint paint = new Paint();
                int iHeight = b.getHeight();
                canvas.drawBitmap(b, 0, iHeight, paint);
                webView.draw(canvas);
                image.setImageBitmap(b);
                if (b != null) {
                    File file = new File("/sdcard/dituidashi/capture2.png");
                    try{
                        file.createNewFile();
                        FileOutputStream osstream = new FileOutputStream(file);
                        b.compress(Bitmap.CompressFormat.PNG,100,osstream);
                        osstream.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress == 100)
                    new Background().execute();
            }
        });
    }
}
