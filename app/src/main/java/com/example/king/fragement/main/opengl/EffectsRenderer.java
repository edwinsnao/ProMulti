package com.example.king.fragement.main.opengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.effect.Effect;
import android.media.effect.EffectContext;
import android.media.effect.EffectFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;

import com.example.king.fragement.R;
import com.example.king.fragement.main.LogWrap;

import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by joe_c on 2015/6/1.
 */
public class EffectsRenderer implements GLSurfaceView.Renderer {

    private Bitmap photo;
    private int photoWidth, photoHeight;
    private int textures[] = new int[2];
    private Square square;
    private BitmapFactory.Options opt;

    private EffectContext effectContext;
    private Effect effect;

    public EffectsRenderer(Context context){
        super();
        photo = BitmapFactory.decodeResource(context.getResources(), R.drawable.girl1);
        photoWidth = photo.getWidth();
        photoHeight = photo.getHeight();

    }
    public EffectsRenderer(Bitmap bitmap){
        super();
        photo = bitmap;
        photoWidth = photo.getWidth();
        photoHeight = photo.getHeight();

    }

    public EffectsRenderer(InputStream is){
        super();
        /*
        * 防止大图片时
        * 内存溢出A/libc: new failed to allocate 2687170482 bytes
        * */
//        if(opt == null) {
//            opt = new BitmapFactory.Options();
//            opt.inPreferredConfig = Bitmap.Config.RGB_565;
//            opt.inJustDecodeBounds = true;
////        }
//        photo = BitmapFactory.decodeStream(is,null,opt);
////        photoWidth = photo.getWidth();
////        photoHeight = photo.getHeight();
//        if(opt.outHeight>=1500 || opt.outWidth>=1500)
//        {
//            opt.inSampleSize = 2;
//            opt.inJustDecodeBounds = false;
//            photo = BitmapFactory.decodeStream(is,null,opt);
//        }
//        /*
//        * 正常图片
//        * */
//        else{
//            opt.inJustDecodeBounds = false;
//            photo = BitmapFactory.decodeStream(is,null,opt);
//        }
//        LogWrap.e("photoWidth"+photo.getWidth());
//        LogWrap.e("photoHeight"+photo.getHeight());

        if(opt == null) {
            opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            opt.inJustDecodeBounds = true;
        }
                    /*
                    * 大图片处理
                    * */
        photo = BitmapFactory.decodeStream(is,null,opt);
//                    bitmapWidth = bitmap.getWidth();
//                    bitmapHeight = bitmap.getHeight();
//                    LogWrap.e("w:---------"+opt.outWidth);
//                    LogWrap.e("h:---------"+opt.outHeight);
//                    if(bitmapWidth>=1500 || bitmapHeight>=1500)
        if(opt.outWidth>=1500 || opt.outHeight>=1500)
        {
            opt.inSampleSize = 2;
            opt.inJustDecodeBounds = false;
            photo = BitmapFactory.decodeStream(is,null,opt);
        }
                    /*
                    * 正常情况
                    * */
        else{
            opt.inJustDecodeBounds = false;
            photo = BitmapFactory.decodeStream(is,null,opt);
        }
    }

    private void generateSquare(){
        GLES20.glGenTextures(2, textures, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, photo, 0);
        square = new Square();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width, height);
        GLES20.glClearColor(0,0,0,1);
        generateSquare();
    }

    private void grayScaleEffect(){
        EffectFactory factory = effectContext.getFactory();
        effect = factory.createEffect(EffectFactory.EFFECT_GRAYSCALE);
        effect.apply(textures[0], photoWidth, photoHeight, textures[1]);
    }

    private void documentaryEffect(){
        EffectFactory factory = effectContext.getFactory();
        effect = factory.createEffect(EffectFactory.EFFECT_DOCUMENTARY);
        effect.apply(textures[0], photoWidth, photoHeight, textures[1]);
    }

    private void brightnessEffect(){
        EffectFactory factory = effectContext.getFactory();
        effect = factory.createEffect(EffectFactory.EFFECT_BRIGHTNESS);
        effect.setParameter("brightness", 2f);
        effect.apply(textures[0], photoWidth, photoHeight, textures[1]);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if(effectContext==null) {
            effectContext = EffectContext.createWithCurrentGlContext();
        }
        if(effect!=null){
            effect.release();
        }
        documentaryEffect();
        square.draw(textures[1]);
    }
}
