package com.example.photogallery;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.GridView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {
    private ArrayList<String> imagePaths;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        gridView = findViewById(R.id.galleryGrid);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(this, DetailActivity.class);
            i.putExtra("path", imagePaths.get(position));
            startActivity(i);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadImages();
    }

    private void loadImages() {
        imagePaths = new ArrayList<>();
        File folder = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyGalleryApp");

        if (folder.exists()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File f : files) {
                    imagePaths.add(f.getAbsolutePath());
                }
            }
        }
        ImageAdapter adapter = new ImageAdapter(this, imagePaths);
        gridView.setAdapter(adapter);
    }
}