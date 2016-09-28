package com.example.king.fragement.main.picTest;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.king.fragement.R;
import com.example.king.fragement.main.LogWrap;
import com.example.king.fragement.main.opengl.EffectsRenderer;

import java.io.FileNotFoundException;

/**
 * Created by Kings on 2016/5/18.
 */
public class ShowPic extends AppCompatActivity{
    private GLSurfaceView glSurfaceView;
    private BitmapFactory.Options opt;
    private  Bitmap bitmap;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContentResolver cr = this.getContentResolver();
        Uri uri = getIntent().getData();
//        String test = getIntent().getStringExtra("uri");
        LogWrap.e("ShowPic-----------"+uri);
        try {
//        if(opt == null) {
            opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            opt.inJustDecodeBounds = true;
//        }
                    /*
                    * 大图片处理
                    * */

            bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri),null,opt);

//                    bitmapWidth = bitmap.getWidth();
//                    bitmapHeight = bitmap.getHeight();
//                    LogWrap.e("w:---------"+opt.outWidth);
//                    LogWrap.e("h:---------"+opt.outHeight);
//                    if(bitmapWidth>=1500 || bitmapHeight>=1500)
        if(opt.outWidth>=1500 || opt.outHeight>=1500)
        {
            opt.inSampleSize = 2;
            opt.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri),null,opt);
        }
                    /*
                    * 正常情况
                    * */
        else{
            opt.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri),null,opt);
        }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setEGLContextClientVersion(2);
        //            glSurfaceView.setRenderer(new EffectsRenderer((cr.openInputStream(uri))));
        glSurfaceView.setRenderer(new EffectsRenderer(bitmap));
        setContentView(glSurfaceView);
    }
}
