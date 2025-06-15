package com.s23010526.asitha; // Ensure this matches your package name

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log; // For logging
import android.view.View;
import android.widget.Toast; // For user feedback

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MultiMediaActivity extends AppCompatActivity {

    private static final String TAG = "MultiMediaActivity"; // Tag for logging
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_multi_media); // Ensure this layout file exists and is correct

        // mediaPlayer is already null by default as a member variable, so dont need --> mediaPlayer = null;

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void controlAudioPlayback(View view) {
        int id = view.getId();
        if (id == R.id.audioPlayButton) {
            startAudio();
        } else if (id == R.id.audioPauseButton) {
            pauseAudio();
        } else if (id == R.id.audioStopButton) {
            // stopping and immediately releasing memory.
            stopAndReleaseAudio();
        }
    }

    //Start audio
    private void startAudio() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.audio_alanwalkerdremer);

            if (mediaPlayer == null) {
                // checking whether the file is present
                Log.e(TAG, "MediaPlayer creation failed. Check res/raw/audio_alanwalkerdremer");
                Toast.makeText(this, "Error: Could not load audio.", Toast.LENGTH_SHORT).show();
                return;
            }

            mediaPlayer.setOnCompletionListener(mp -> {
                Log.d(TAG, "Playback completed.");
                Toast.makeText(MultiMediaActivity.this, "Playback Finished", Toast.LENGTH_SHORT).show();
                releaseMediaPlayer(); // Relese method
            });


        }

        // Start playback only if not already playing
        if (!mediaPlayer.isPlaying()) {
            try {
                mediaPlayer.start();
                Log.d(TAG, "MediaPlayer started.");
                Toast.makeText(this, "Playing Audio", Toast.LENGTH_SHORT).show();
            } catch (IllegalStateException e) {
                Log.e(TAG, "MediaPlayer.start() failed: " + e.getMessage());
                Toast.makeText(this, "Error starting playback.", Toast.LENGTH_SHORT).show();
                // Attempt to recover by releasing the player
                releaseMediaPlayer();
            }
        } else {
            Log.d(TAG, "MediaPlayer is already playing.");
        }
    }

    private void pauseAudio() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            Log.d(TAG, "MediaPlayer paused.");
            Toast.makeText(this, "Audio Paused", Toast.LENGTH_SHORT).show();
        } else {
            Log.d(TAG, "MediaPlayer is not playing or is null, cannot pause.");
        }
    }

    private void stopAndReleaseAudio() {
        if (mediaPlayer != null) {
            Log.d(TAG, "Stopping and releasing MediaPlayer.");
            Toast.makeText(this, "Audio Stopped", Toast.LENGTH_SHORT).show();
            releaseMediaPlayer(); // Unified method to stop, release, and nullify
            // TODO: Update UI (e.g., reset all button states)
        } else {
            Log.d(TAG, "MediaPlayer is already null, cannot stop.");
        }
    }

// media player Relese method
    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop(); // Stop playback if it's ongoing
                }
                mediaPlayer.release(); // Release the native MediaPlayer resources
            } catch (IllegalStateException e) {
                // but release() should generally be safe.
            } catch (Exception e) {
                // Catch any other unexpected exceptions during release
            } finally {
                mediaPlayer = null; // Important: Set the Java object reference to null
            }
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop called.");
        if (isFinishing()) {
            Log.d(TAG, "Activity is finishing in onStop, releasing MediaPlayer.");
            releaseMediaPlayer();
        } else {
            Log.d(TAG, "Activity not finishing in onStop. Current behavior: MediaPlayer not changed here " +
                    "unless activity is explicitly finishing (handled above).");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called. Releasing MediaPlayer.");
        // Always release the MediaPlayer in onDestroy to prevent resource leaks.
        releaseMediaPlayer();
    }
}