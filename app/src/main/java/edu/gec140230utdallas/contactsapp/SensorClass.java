package edu.gec140230utdallas.contactsapp;

//
// Gage Cottom

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import static android.content.Context.SENSOR_SERVICE;

public class SensorClass implements SensorEventListener{

    Context contxt;
    MainScreen ms;
    private final SensorManager senSensorManager;
    private final Sensor senAccelerometer;

    private long timeEvent = 0;
    private float x0, y0, z0;
    private long lastShake = 0;

    private static final int SHAKE_THRESHOLD = 750;

    SensorClass(Context cont, MainScreen act)
    {
        contxt = cont;
        ms = act;

        senSensorManager = (SensorManager)contxt.getSystemService(SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void register() {

        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregister() {
        senSensorManager.unregisterListener(this);

    }

    //Gage Cottom
    //Handles accelerometer changes
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x1 = sensorEvent.values[0];
            float y1 = sensorEvent.values[1];
            float z1 = sensorEvent.values[2];

            long timeCurrent = System.currentTimeMillis();

            if (((timeCurrent - timeEvent) > 200) && ((timeCurrent - lastShake) > 1000)) {
                long timeDelta = (timeCurrent - timeEvent);
                timeEvent = timeCurrent;

                float moveSpeed = Math.abs(x1 + y1 + z1 - x0 - y0 - z0)/ timeDelta * 10000;

                if (moveSpeed > SHAKE_THRESHOLD) {
                    lastShake = timeCurrent;
                    ms.onShake();

                }

                x0 = x1;
                y0 = y1;
                z0 = z1;
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //do nothing. this is required for sensors
    }

}