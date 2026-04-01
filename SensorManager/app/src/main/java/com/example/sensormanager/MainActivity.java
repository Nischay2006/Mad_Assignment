package com.example.sensormanager;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer, lightSensor, proximitySensor;
    private TextView accelText, lightText, proximityText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accelText = findViewById(R.id.accel_text);
        lightText = findViewById(R.id.light_text);
        proximityText = findViewById(R.id.proximity_text);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Initialize Sensors
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelText.setText("Accel: \nX: " + event.values[0] +
                    "\nY: " + event.values[1] +
                    "\nZ: " + event.values[2]);
        } else if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            lightText.setText("Light Level: " + event.values[0] + " lx");
        } else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            proximityText.setText("Proximity: " + event.values[0] + " cm");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed for this basic implementation
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register all listeners
        if (accelerometer != null)
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        if (lightSensor != null)
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        if (proximitySensor != null)
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister to save battery
        sensorManager.unregisterListener(this);
    }
}