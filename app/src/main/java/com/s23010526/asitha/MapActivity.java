package com.s23010526.asitha;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MapActivity extends AppCompatActivity {

    private EditText editTextAddress;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_map);

        editTextAddress = findViewById(R.id.addressInput);
        btnSearch = findViewById(R.id.mapSearchButton);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Getting the text from the text box
                String address = editTextAddress.getText().toString().trim();

                // Check if the address is empty
                if (address.isEmpty()) {
                    Toast.makeText(MapActivity.this, "Please enter an address", Toast.LENGTH_SHORT).show();
                } else {
                    // If it is not empty, call the openMap method
                    openMap(address);
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void openMap(String address) {
        try {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));

            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

            mapIntent.setPackage("com.google.android.apps.maps");

            // Start the activity
            startActivity(mapIntent);

        } catch (ActivityNotFoundException e) {
            // If Google Maps is not installed, launch the Play Store to install it
            Uri playStoreUri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps&hl=en&gl=US"); //
            Intent playStoreIntent = new Intent(Intent.ACTION_VIEW, playStoreUri);
            startActivity(playStoreIntent);

            // Inform the user with error massage
            Toast.makeText(this, "Google Maps is not installed. Please install it to use this feature.", Toast.LENGTH_LONG).show();
        }
    }
}