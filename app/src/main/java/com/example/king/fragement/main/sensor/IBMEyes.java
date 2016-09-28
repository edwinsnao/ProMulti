package com.example.king.fragement.main.sensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.king.fragement.R;
import com.example.king.fragement.main.LogWrap;

/**
 * Created by Kings on 2016/4/19.
 */
public class IBMEyes extends AppCompatActivity implements SensorListener {

    final String tag = "IBMEyes";
    SensorManager sm = null;

    TextView View1 = null;
    TextView View2 = null;
    TextView View3 = null;
    TextView View4 = null;
    TextView View5 = null;
    TextView View6 = null;
    TextView View7 = null;
    TextView View8 = null;
    TextView View9 = null;
    TextView View10 = null;
    TextView View11 = null;
    TextView View12 = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        setContentView(R.layout.sensor);
        View1 = (TextView) findViewById(R.id.edt1);
        View2 = (TextView) findViewById(R.id.edt2);
        View3 = (TextView) findViewById(R.id.edt3);
        View4 = (TextView) findViewById(R.id.edt4);
        View5 = (TextView) findViewById(R.id.edt5);
        View6 = (TextView) findViewById(R.id.edt6);
        View7 = (TextView) findViewById(R.id.edt7);
        View8 = (TextView) findViewById(R.id.edt8);
        View9 = (TextView) findViewById(R.id.edt9);
        View10 = (TextView) findViewById(R.id.edt10);
        View11 = (TextView) findViewById(R.id.edt11);
        View12 = (TextView) findViewById(R.id.edt12);
    }

    public void onSensorChanged(int sensor, float[] values) {
        synchronized (this) {
            String str =  "X：" + values[0] + "，Y：" + values[1] + "，Z：" + values[2];
            switch (sensor){
                case Sensor.TYPE_ACCELEROMETER:
                    View1.setText("加速度：" + str);
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    View2.setText("磁场：" + str);
                    break;
                case Sensor.TYPE_ORIENTATION:
                    View3.setText("定位：" + str);
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    View4.setText("陀螺仪：" + str);
                    break;
                case Sensor.TYPE_LIGHT:
                    View5.setText("光线：" + str);
                    break;
                case Sensor.TYPE_PRESSURE:
                    View6.setText("压力：" + str);
                    break;
                case Sensor.TYPE_TEMPERATURE:
                    View7.setText("温度：" + str);
                    break;
                case Sensor.TYPE_PROXIMITY:
                    View8.setText("距离：" + str);
                    break;
                case Sensor.TYPE_GRAVITY:
                    View9.setText("重力：" + str);
                    break;
                case Sensor.TYPE_LINEAR_ACCELERATION:
                    View10.setText("线性加速度：" + str);
                    break;
                case Sensor.TYPE_ROTATION_VECTOR:
                    View11.setText("旋转矢量：" + str);
                    break;
                default:
                    View12.setText("NORMAL：" + str);
                    break;
            }
        }
    }

    public void onAccuracyChanged(int sensor, int accuracy) {
        LogWrap.d(tag+"onAccuracyChanged: " + sensor + ", accuracy: " + accuracy);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this,
                Sensor.TYPE_ACCELEROMETER |
                        Sensor.TYPE_MAGNETIC_FIELD |
                        Sensor.TYPE_ORIENTATION |
                        Sensor.TYPE_GYROSCOPE |
                        Sensor.TYPE_LIGHT |
                        Sensor.TYPE_PRESSURE |
                        Sensor.TYPE_TEMPERATURE |
                        Sensor.TYPE_PROXIMITY |
                        Sensor.TYPE_GRAVITY |
                        Sensor.TYPE_LINEAR_ACCELERATION |
                        Sensor.TYPE_ROTATION_VECTOR,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        sm.unregisterListener(this);
        super.onStop();
    }

}
