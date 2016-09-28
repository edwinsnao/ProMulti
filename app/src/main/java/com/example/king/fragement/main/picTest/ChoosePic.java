package com.example.king.fragement.main.picTest;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.king.fragement.R;
import com.example.king.fragement.main.LogWrap;
import com.example.king.fragement.main.opengl.EffectsRenderer;

import java.io.FileNotFoundException;

/**
 * Created by Kings on 2016/4/10.
 */
public class ChoosePic extends AppCompatActivity{
    private Button choose;
    private ImageView img;
    private Bitmap bitmap;
    private BitmapFactory.Options opt;
    private int  bitmapWidth;
    private int bitmapHeight;
//    private GLSurfaceView glSurfaceView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_pic);
        initView();
    }

    private void initView() {
//        glSurfaceView = (GLSurfaceView) findViewById(R.id.surfaceview_opengl);
//        glSurfaceView.setEGLContextClientVersion(20);
        // Render the view only when there is a change in the drawing data
        img = (ImageView) findViewById(R.id.img_opengl);
        choose = (Button) findViewById(R.id.pic);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Intent.ACTION_GET_CONTENT);
                if (Build.VERSION.SDK_INT <19) {
                    it.setAction(Intent.ACTION_GET_CONTENT);
                }else {
                    it.setAction(Intent.ACTION_OPEN_DOCUMENT);
                }

                it.setType("image/*");
                it.putExtra("crop",true);
                it.putExtra("return-data",true);
                startActivityForResult(it,2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            LogWrap.e("requestCode"+requestCode);
            if(requestCode == 2){
                Uri uri = data.getData();
//                LogWrap.e("Uri"+uri.getPath());
//                Uri/external/images/media/29
               /* 04-10 13:49:58.818 1774-1774/com.example.king.fragement E/MyAppTag: requestCode2
                04-10 13:49:58.818 1774-1774/com.example.king.fragement E/MyAppTag: Urimedia
                04-10 13:49:58.818 1774-1774/com.example.king.fragement E/MyAppTag: Urimedia
                04-10 13:49:58.818 1774-1774/com.example.king.fragement E/MyAppTag: Urinull
                04-10 13:49:58.818 1774-1774/com.example.king.fragement E/MyAppTag: Urimedia
                04-10 13:49:58.818 1774-1774/com.example.king.fragement E/MyAppTag: Uri29
                04-10 13:49:58.818 1774-1774/com.example.king.fragement E/MyAppTag: Uri-1*/
//                LogWrap.e("Uri"+uri.getAuthority());
//                LogWrap.e("Uri"+uri.getEncodedAuthority());
//                LogWrap.e("Uri"+uri.getFragment());
//                LogWrap.e("Uri"+uri.getHost());
//                LogWrap.e("Uri"+uri.getLastPathSegment());
//                LogWrap.e("Uri"+uri.getPort());
                ContentResolver cr = this.getContentResolver();
                try{
//                    glSurfaceView.setEGLContextClientVersion(20);
//                    glSurfaceView.setRenderer(new EffectsRenderer(cr.openInputStream(uri)));
//                    img.setVisibility(View.GONE);
//                    glSurfaceView.setVisibility(View.VISIBLE);
                    LogWrap.e("uri--------------+"+uri);
//                    if(opt == null) {
                        opt = new BitmapFactory.Options();
                        opt.inPreferredConfig = Bitmap.Config.RGB_565;
                        opt.inJustDecodeBounds = true;
//                    }
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
//                    if(opt == null) {
//                        opt = new BitmapFactory.Options();
//                        opt.inPreferredConfig = Bitmap.Config.RGB_565;
//                    }
//                    if(bitmap == null)
//                        bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri),null,opt);
//                    else{
//                        if(!bitmap.isRecycled()){
//                            bitmap.recycle();
//                            bitmap = null;
//                            bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri),null,opt);
//                        }
//                    }
                    /*
                    * 需要minSdk19
                    * */
//                    bitmap.setConfig(Bitmap.Config.RGB_565);
                    img.setImageBitmap(bitmap);
//                    LogWrap.e("photoWidth"+bitmap.getWidth());
//                    LogWrap.e("bitmapHeight"+bitmap.getHeight());
                    Intent it = new Intent(ChoosePic.this,ShowPic.class);
//                    it.putExtra("uri",uri);
                    it.setData(uri);
                    startActivity(it);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
