package com.example.aswitharajendran.shake;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  implements SensorEventListener {
    private SensorManager mSensorManager,sm;



    private Sensor mAccelerometer,accelerometer;
    private ShakeDetector mShakeDetector;
    TextView x_axis,y_axis,z_axis;
    Float x,y,z;
    Button button;
    String getter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        x_axis=(TextView)findViewById(R.id.x_acceleration);
        y_axis=(TextView)findViewById(R.id.y_acceleration);
        z_axis=(TextView)findViewById(R.id.z_acceleration);

        mShakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {
                handleShake();
            }
        });
        TextView showFactButton = (TextView) findViewById(R.id.clickButton);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getter = "1";
            }
        };
        showFactButton.setOnClickListener(listener);
    }

    @Override
    public void onResume(){
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }
    @Override
    public void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(mShakeDetector);

    }


    private void handleShake() {
        if(getter == "1") {
            Toast.makeText(getApplicationContext(), "Welcome, PreethiKumar", Toast.LENGTH_LONG).show();
            getter = "0";
        }else{
            Toast.makeText(getApplicationContext(), "New Pattern Recorded.", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        x_axis.setText("X: " + event.values[0]);
        y_axis.setText("Y: "+  event.values[1]);
        z_axis.setText("Z: "+  event.values[2]);
        x=event.values[0];
        y=event.values[1];
        z=event.values[2];
        //x=2.2f;
        String xaxis=Float.toString(x);
        //y=2.365f;
        String yaxis=Float.toString(y);
        //z=8.2145f;
        String zaxis=Float.toString(z);
        String method="Register";
       BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(method,xaxis,yaxis,zaxis);
        //finish();

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
