package com.s23010526.asitha;

import android.os.Bundle;

import android.content.Intent;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {
// +++++++++++++++++++++++++++++Variables++++++++++++++++++++++++++++++++++++
    MaterialButton mapButton;
    MaterialButton multiMediaButton;
    MaterialButton sensorButton;
// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

// ++++++++++++++++++++++++++Map Page+++++++++++++++++++++++++++++++++++++++++
        //map Button Id
        mapButton = findViewById(R.id.toMapBtn);

        // setting onclick listnesr for Map Button
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // creating instent for mapactivity to start
                Intent intent = new Intent(HomeActivity.this, MapActivity.class);
                startActivity(intent);
                Toast.makeText(HomeActivity.this, "You Are Visiting Map Page!", Toast.LENGTH_SHORT).show();

            }
        });
// +++++++++++++++++++++++++++++++Multimedia Page++++++++++++++++++++++++++++
        //Multimeda buttin id
        multiMediaButton = findViewById(R.id.toMultimedia);
        // Set Multimedia btn Onclick listner
        multiMediaButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                      Intent intent = new Intent(HomeActivity.this, MultiMediaActivity.class);
                      startActivity(intent);
                      Toast.makeText(HomeActivity.this, "You Are Visiting Multimedia Page!", Toast.LENGTH_SHORT).show();
                 }
             });


// ++++++++++++++++++++++++++Sensor Page+++++++++++++++++++++++++++++++++++++
        // sensor button Id
        sensorButton = findViewById(R.id.toSensor);
        // Set Sensor test btnOnclick listner
        sensorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SensorActivity.class);
                startActivity(intent);
                Toast.makeText(HomeActivity.this, "You Are Visiting Sensors Page!", Toast.LENGTH_SHORT).show();
            }
        });


// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
// last updated date 2025/6/15
//By Asitha Kanchana