package com.s23010526.asitha;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//Sensor Imports

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.widget.TextView;
import android.widget.Toast;


public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor tempSensor;
    private TextView tvTemprature;

    private final float TEMPRATURE_TRESHOLD = 26.0f;

//    My Student Number Is S23010526 so last two Numbers are 26

    private boolean isTempSensorAvailable;
    private boolean isSoundPlaying = false;
    private MediaPlayer mediaPlayer; // Declire Media Player

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // onCreate method remains the same
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sensor);

        tvTemprature = findViewById(R.id.tempLable);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


        //check and get temprature sensor
        if(sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isTempSensorAvailable = true;
        }else {
            tvTemprature.setText("Temprature Sensor Is Not Avalble or Not Working!");
            isTempSensorAvailable = false;
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // onSensorChanged implimentation
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            float temprature = event.values[0];
            tvTemprature.setText(String.format("%2f °C", temprature));


//            checking wether the temprature value pass the tresh hold
            if (temprature > TEMPRATURE_TRESHOLD && !isSoundPlaying){
                playSound();
            }
        }

    }


    private void playSound() {
        if (mediaPlayer == null) {
            // media player with  offline audio clip
            mediaPlayer = MediaPlayer.create(this, R.raw.audio_alanwalkerdremer);

            if (mediaPlayer == null) {
                // if media player creation failed then following will happend
                isSoundPlaying = false;
                Toast.makeText(this, "Error: Could not load audio.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        //reste sound before start

        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            isSoundPlaying = true;

            mediaPlayer.setOnCompletionListener(mp -> {
                isSoundPlaying = false;
            });
        }
    }

    // onAccuracyChanged methods
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



    // onResume and onPause methods
    @Override
    protected void onResume() {
        super.onResume();
        if (isTempSensorAvailable) {
            // Register the sensor listener
            sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isTempSensorAvailable) {
            // Unregister the listener whn activity is not not in focus
            sensorManager.unregisterListener(this);
        }
        // Stop and release the media player when the app is paused
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
            isSoundPlaying = false;
        }
    }
}