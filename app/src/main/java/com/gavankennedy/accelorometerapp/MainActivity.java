package com.gavankennedy.accelorometerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.usage.UsageEvents;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    TextView X;
    TextView Y;
    TextView Z;
    private SensorManager mSM;
    private Sensor mSens;
    private boolean isFlat = false;
    final MediaPlayer mp=MediaPlayer.create(this,R.raw.video_play_back);
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        X = findViewById(R.id.tv_XCoordinates);
        Y = findViewById(R.id.tv_YCoordinates);
        Z = findViewById(R.id.tv_ZCoordinates);

        mSM = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSens = mSM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSM.registerListener(this,mSens,mSM.SENSOR_DELAY_NORMAL);



    }
        protected void onResume (View view){
            super.onResume();
            mSM.registerListener((SensorEventListener) this, mSens, SensorManager.SENSOR_DELAY_NORMAL);
        }

        protected void onPause (View view){
            super.onPause();
            mSM.unregisterListener((SensorEventListener) this);
        }



    public void onReset(View view)
    {
        isFlat=false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = Math.abs(event.values[0]);
        float y = Math.abs(event.values[1]);
       float z = Math.abs(event.values[2]);
       X.setText(String.valueOf(x));
       Y.setText(String.valueOf(y));
       Z.setText(String.valueOf(z));
        if (x < 1 && y < 1 && z > 9) {
            if (isFlat == false) {
                isFlat = true;
                Toast.makeText(this, "Much Better", Toast.LENGTH_SHORT).show();
            }
            mp.start();
        }
        if(x>1&&y>1&&z<9)
        {
            if (isFlat == true) {
                isFlat = false;
                Toast.makeText(this, "Put this phone down ya big smelly!!", Toast.LENGTH_SHORT).show();
            }
            mp.stop();
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
