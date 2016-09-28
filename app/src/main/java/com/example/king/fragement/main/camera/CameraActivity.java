package com.example.king.fragement.main.camera;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.media.ExifInterface;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.king.fragement.R;


public class CameraActivity extends Activity implements SurfaceHolder.Callback {
    /**
     * putExtra
     */
    public static final String EXTRA_PATH = "Path";
    //    public static final String EXTRA_CATEGORY = "Category";
    //	传递图片保存的路径
    private String path;
    //传递textview的字符串
//    private String category;
    // 预览尺寸 默认设置
    public int WIDTH = 320;// 640;//1024;
    public int HEIGHT = 240;// 480;//768;
    // 拍摄尺寸 默认设置
    public int srcwidth = 2048;// 1600;//2048;final
    public int srcheight = 1536;// 1200;//1536;final
    private TextView takeRecogText;
    private TextView lightText;
    private Camera camera;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private ToneGenerator tone;
    private List<String> focusModes;
    private ImageView topLeft, topRight, bottomLeft, bottomRight;
    private SensorManager sManager = null;
    private SensorEventListener myListener = null;
    private int count = 0;
    private boolean light_on = false;
    private int degree_sensor = 0;
    private boolean isPortrait = false;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // 三星在对焦设置为两秒的时候会抛出异常
            handler.postDelayed(this, 3000);
            autoFocus();

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        handler.postDelayed(runnable, 3000);
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        path = getIntent().getStringExtra(EXTRA_PATH);
        path = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"edwinsnao";
//        category = getIntent().getStringExtra(EXTRA_CATEGORY);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_camera);
        // 设置拍摄尺寸
        Intent intentget = this.getIntent();
        srcwidth = intentget.getIntExtra("srcwidth", 2048);
        srcheight = intentget.getIntExtra("srcheight", 1536);
        WIDTH = intentget.getIntExtra("WIDTH", 640);
        HEIGHT = intentget.getIntExtra("HEIGHT", 480);
//        recogType = intentget.getIntExtra("recogType", 1);
        findview();
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(CameraActivity.this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        sManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // 监听传感器事件
        myListener = new SensorEventListener() {
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }

            public void onSensorChanged(SensorEvent event) {
                if (count < 5) {
                    count++;
                }
//                if (Sensor.TYPE_ACCELEROMETER != event.sensor.getType()) {
//                    return;
//                }
//
//                float[] values = event.values;
//                float ax = values[0];
//                float ay = values[1];
//
//                double g = Math.sqrt(ax * ax + ay * ay);
//                double cos = ay / g;
//                if (cos > 1) {
//                    cos = 1;
//                } else if (cos < -1) {
//                    cos = -1;
//                }
//                double rad = Math.acos(cos);
//                if (ax < 0) {
//                    rad = 2 * Math.PI - rad;
//                }
//
//                int uiRot = getWindowManager().getDefaultDisplay().getRotation();
//                double uiRad = Math.PI / 2 * uiRot;
//                rad -= uiRad;
//                Log.d("degree",String.valueOf(rad));
//                if(rad>4)
//                    degree_sensor = 90;
                  final int _DATA_X = 0;
                  final int _DATA_Y = 1;
                  final int _DATA_Z = 2;

                 final int ORIENTATION_UNKNOWN = -1;
                float[] values = event.values;
                int orientation = ORIENTATION_UNKNOWN;
                float X = -values[_DATA_X];
                float Y = -values[_DATA_Y];
                float Z = -values[_DATA_Z];
                float magnitude = X * X + Y * Y;
                // Don't trust the angle if the magnitude is small compared to the y
                // value
                if (magnitude * 4 >= Z * Z) {
                    // 屏幕旋转时
                    float OneEightyOverPi = 57.29577957855f;
                    float angle = (float) Math.atan2(-Y, X) * OneEightyOverPi;
                    orientation = 90 - (int) Math.round(angle);
                    // normalize to 0 - 359 range
                    while (orientation >= 360) {
                        orientation -= 360;
                    }
                    while (orientation < 0) {
                        orientation += 360;
                    }
                }
                if (orientation > 45 && orientation < 135) {

                } else if (orientation > 135 && orientation < 225) {

                } else if (orientation > 225 && orientation < 315) {
                    if (isPortrait) {
//                        Log.e(test, 切换成横屏);
                        isPortrait = false;
                    }
                } else if ((orientation > 315 && orientation < 360) || (orientation > 0 && orientation < 45)) {
                    if (!isPortrait) {
//                        Log.e(test,切换成竖屏);
                        isPortrait = true;
                    }
                }
                Log.d("isPortait",String.valueOf(isPortrait));
            }
        };
        sManager.registerListener(myListener,
                sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void findview() {
        ClickListener listener = new ClickListener();
        TextView backResetText = (TextView) findViewById(R.id.back_and_reset_text);
//        backResetText.setTextColor(Color.BLACK);
        backResetText.setOnClickListener(listener);
        takeRecogText = (TextView) findViewById(R.id.take_and_confirm_text);
//        takeRecogText.setTextColor(Color.BLACK);
        takeRecogText.setOnClickListener(listener);
        lightText = (TextView) findViewById(R.id.light_on_off_text);
//        lightText.setTextColor(Color.BLACK);
        lightText.setOnClickListener(listener);
//        tagText = (TextView) findViewById(R.id.tv_tag);
//        if (category != null) {
//            tagText.setText(category);
//        }

        topLeft = (ImageView) findViewById(R.id.topleft);
        topRight = (ImageView) findViewById(R.id.topright);
        bottomLeft = (ImageView) findViewById(R.id.bottomleft);
        bottomRight = (ImageView) findViewById(R.id.bottomright);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceViwe);
        surfaceView.post(new Runnable() {
            @Override
            public void run() {
                autoFocus();
            }
        });
        showFourImageView();
    }


    private void hideFourImageView() {
        topLeft.setVisibility(View.INVISIBLE);
        topRight.setVisibility(View.INVISIBLE);
        bottomLeft.setVisibility(View.INVISIBLE);
        bottomRight.setVisibility(View.INVISIBLE);
    }

    private void showFourImageView() {
        topLeft.setVisibility(View.VISIBLE);
        topRight.setVisibility(View.VISIBLE);
        bottomLeft.setVisibility(View.VISIBLE);
        bottomRight.setVisibility(View.VISIBLE);

    }

    private class ClickListener implements OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.back_and_reset_text:

//                    closeCamera();
//                    handler.removeCallbacks(runnable);// 停止计时器，每当拍照或退出时都要执行这段代码。
//                    Intent intent = new Intent(CameraActivity.this,
//                            MainActivity.class);
//                    CameraActivity.this.finish();
//                    startActivity(intent);
                    onBackPressed();

                    break;
                // 拍照
                case R.id.take_and_confirm_text:
//                    handler.removeCallbacks(runnable);// 停止计时器，每当拍照或退出时都要执行这段代码。
                    takePicture();
                    break;
                case R.id.light_on_off_text:
                    if (light_on) {
                        lightText.setText(getString(R.string.light1_string));
//                        lightText.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.lighton, 0, 0);
                        // 关闭闪光灯
                        Parameters parameters2 = camera.getParameters();
                        parameters2.setFlashMode(Parameters.FLASH_MODE_OFF);
                        camera.setParameters(parameters2);
                        light_on = false;
                    } else {
                        lightText.setText(getString(R.string.light2_string));
//                        lightText.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.lightoff, 0, 0);
                        // 开启闪光灯
                        Parameters parameters2 = camera.getParameters();
                        parameters2.setFlashMode(Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(parameters2);
                        light_on = true;
                    }
                    break;
            }
        }
    }


    /* 拍照对焦 */
    public void takePicture() {
        if (camera != null) {
            try {
                if (focusModes != null
                        && focusModes
                        .contains(Parameters.FOCUS_MODE_AUTO)) {
                    camera.autoFocus(new AutoFocusCallback() {
                        public void onAutoFocus(boolean success, Camera camera) {

                            if (success) {
                                if (count == 5) {
                                    camera.takePicture(shutterCallback, null,
                                            PictureCallback);
                                }
                            } else {
                                if (count == 5) {
                                    camera.takePicture(shutterCallback, null,
                                            PictureCallback);
                                }
                            }

                        }
                    });
                } else {
                    camera.takePicture(shutterCallback, null, PictureCallback);
                }

            } catch (Exception e) {
                e.printStackTrace();
                camera.stopPreview();
                camera.startPreview();
                takeRecogText.setEnabled(true);
                Toast.makeText(this, R.string.toast_autofocus_failure,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 快门按下的时候onShutter()被回调拍照声音
    private ShutterCallback shutterCallback = new ShutterCallback() {
        public void onShutter() {
            if (tone == null)
                // 发出提示用户的声音
                tone = new ToneGenerator(1,// AudioManager.AUDIOFOCUS_REQUEST_GRANTED
                        ToneGenerator.MIN_VOLUME);
            tone.startTone(ToneGenerator.TONE_PROP_BEEP);
        }
    };

    /* 拍照 */
    private PictureCallback PictureCallback = new PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            count = 0;
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            int SampleSize = computeSampleSize(opts, -1, 2048 * 1536);
            opts.inSampleSize = SampleSize;
            opts.inJustDecodeBounds = false;
            opts.inPurgeable = true;
            opts.inInputShareable = true;
            try {
                Field field = BitmapFactory.Options.class
                        .getDeclaredField("inNativeAlloc");
                field.set(opts, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, opts);
            if (srcwidth == 2048 && srcheight == 1536) {
                Matrix matrix = new Matrix();
                /**
                * 判断横竖屏
                * */
                if(isPortrait)
                matrix.postRotate(90);
                matrix.postScale(0.625f, 0.625f);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);
            }
            if (srcwidth == 1600 && srcheight == 1200) {
                Matrix matrix = new Matrix();
//                TODO
//                int degree = BitmapUtils.readPictureDegree(path);
                if(isPortrait)
                matrix.postRotate(90);
                matrix.postScale(0.8f, 0.8f);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);
            }
            /* 创建文件 */
            File myCaptureFile = new File(path);

            try {
                if (!myCaptureFile.exists()) {
                    myCaptureFile.createNewFile();
                }
                BufferedOutputStream bos = new BufferedOutputStream(
                        new FileOutputStream(path));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//                int degree = BitmapUtils.readPictureDegree(path);
//                ExifInterface exifInterface = new ExifInterface(path);
//                exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
//                        ExifInterface.ORIENTATION_NORMAL);
//                String orientation = exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION);
//                Log.d("exif_degree",String.valueOf(degree_sensor));
//                Log.d("orientation",orientation);
//                Log.d("degree",String.valueOf(degree));
//                exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION,Integer.toString(degree_sensor));
//                try {
//                    exifInterface.saveAttributes();
//                } catch (IOException e) {
//                    Log.e("exif", "cannot save exif", e);
//                }
//                Bitmap result = BitmapUtils.rotateBitmap(bitmap,degree);
//                degree = BitmapUtils.readPictureDegree(path);
//                exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
//                        ExifInterface.ORIENTATION_NORMAL);
//                orientation = exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION);
//                Log.d("exif_degree_after",String.valueOf(degree_sensor));
//                Log.d("orientation_after",orientation);
//                Log.d("degree_after",String.valueOf(degree));
                bos.flush();
                bos.close();
                // 隐藏焦点图片和行驶证外框
                {
                    hideFourImageView();
                }
                resetCamera();
//                Intent it = new Intent();
//                it.setData(Uri.parse(path));
//                setResult(RESULT_OK, it);
                finish();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };


    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        if (camera != null) {
            try {
                Parameters parameters = camera.getParameters();
                if (light_on == true) {
                    parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
                } else {
                    parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
                }

//                if(isPortrait == true){
//                    camera.setDisplayOrientation(90);
//                }
                parameters.setPictureFormat(PixelFormat.JPEG);
                parameters.setPictureFormat(PixelFormat.JPEG);
                parameters.setPictureSize(srcwidth, srcheight);
                camera.setParameters(parameters);
                camera.setPreviewDisplay(holder);
                camera.startPreview();
                focusModes = parameters.getSupportedFocusModes();
            } catch (IOException e) {
                camera.release();
                camera = null;
                e.printStackTrace();
            }
        }
    }

    // 在surface创建时激发
    public void surfaceCreated(SurfaceHolder holder) {
        // 获得Camera对象
        takeRecogText.setEnabled(true);
        if (null == camera) {
            camera = Camera.open();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            // isContinue = false;
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    /* 相机重置 */
    private void resetCamera() {
        if (camera != null) {
            camera.stopPreview();
        }
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (myListener != null) {
            sManager.unregisterListener(myListener);
        }
        sManager = null;
        myListener = null;
    }

    public void autoFocus() {

        if (camera != null) {
            try {
                if (camera.getParameters().getSupportedFocusModes() != null
                        && camera.getParameters().getSupportedFocusModes()
                        .contains(Parameters.FOCUS_MODE_AUTO)) {
                    camera.autoFocus(new AutoFocusCallback() {
                        public void onAutoFocus(boolean success, Camera camera) {
                            if (success) {

                            } else {

                            }
                        }
                    });
                } else {

                    Toast.makeText(getBaseContext(),
                            getString(R.string.unsupport_auto_focus),
                            Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                camera.stopPreview();
                camera.startPreview();
                takeRecogText.setEnabled(true);
                Toast.makeText(this, R.string.toast_autofocus_failure + "黄震",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void closeCamera() {
        synchronized (this) {
            try {
                if (camera != null) {
                    camera.stopPreview();
                    camera.release();
                    camera = null;
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void onBackPressed() {
        closeCamera();
        super.onBackPressed();
    }
}