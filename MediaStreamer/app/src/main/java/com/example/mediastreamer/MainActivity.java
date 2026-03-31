package com.example.mediastreamer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer; // For Audio (Disk)
    VideoView videoView;     // For Video (URL)
    TextView tvStatus;
    boolean isVideoActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Link the UI components from XML
        videoView = findViewById(R.id.video_view);
        tvStatus = findViewById(R.id.tv_status);

        Button btnOpenAudio = findViewById(R.id.btn_open_file);
        Button btnOpenVideo = findViewById(R.id.btn_open_url);
        Button btnPlay = findViewById(R.id.btn_play);
        Button btnPause = findViewById(R.id.btn_pause);
        Button btnStop = findViewById(R.id.btn_stop);
        Button btnRestart = findViewById(R.id.btn_restart);

        // 2. Button Logic: OPEN AUDIO FILE (From res/raw)
        btnOpenAudio.setOnClickListener(v -> {
            stopAllMedia();
            isVideoActive = false;
            // sample_audio must be an mp3 file in your res/raw folder
            mediaPlayer = MediaPlayer.create(this, R.raw.sample_audio);
            tvStatus.setText("Status: Audio Loaded from Disk");
            Toast.makeText(this, "Audio Loaded", Toast.LENGTH_SHORT).show();
        });

        // 3. Button Logic: STREAM VIDEO FROM URL
        btnOpenVideo.setOnClickListener(v -> {
            stopAllMedia();
            isVideoActive = true;
            // This is a sample MP4 URL for testing
            String videoUrl = "https://www.w3schools.com/html/mov_bbb.mp4";
            videoView.setVideoURI(Uri.parse(videoUrl));
            tvStatus.setText("Status: Video URL Loaded");
            Toast.makeText(this, "Video Streaming Ready", Toast.LENGTH_SHORT).show();
        });

        // 4. Button Logic: PLAY
        btnPlay.setOnClickListener(v -> {
            if (isVideoActive) {
                videoView.start();
            } else if (mediaPlayer != null) {
                mediaPlayer.start();
            }
            tvStatus.setText("Status: Playing");
        });

        // 5. Button Logic: PAUSE
        btnPause.setOnClickListener(v -> {
            if (isVideoActive) {
                videoView.pause();
            } else if (mediaPlayer != null) {
                mediaPlayer.pause();
            }
            tvStatus.setText("Status: Paused");
        });

        // 6. Button Logic: STOP
        btnStop.setOnClickListener(v -> stopAllMedia());

        // 7. Button Logic: RESTART
        btnRestart.setOnClickListener(v -> {
            if (isVideoActive) {
                videoView.seekTo(0);
                videoView.start();
            } else if (mediaPlayer != null) {
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
            }
            tvStatus.setText("Status: Restarted");
        });
    }

    // This method cleans up memory and stops any playing media
    private void stopAllMedia() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (videoView != null && videoView.isPlaying()) {
            videoView.stopPlayback();
        }
        tvStatus.setText("Status: Media Stopped");
    }

    // Clean up if the app is closed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAllMedia();
    }
}