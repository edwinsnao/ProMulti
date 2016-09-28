package com.example.king.fragement.main.opengl;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.king.fragement.R;

/**
 * Created by Kings on 2016/5/10.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLSurfaceView surfaceView = new GLSurfaceView(this);
        setContentView(surfaceView);
        surfaceView.setEGLContextClientVersion(2);
        surfaceView.setRenderer(new EffectsRenderer(this));
//        surfaceView.setRenderer(new MyGLRenderer());
    }
}
