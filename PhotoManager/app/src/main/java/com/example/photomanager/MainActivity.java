package com.example.photomanager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageAdapter adapter;
    private ArrayList<File> imageFiles = new ArrayList<>();
    private File targetFolder;
    private ActivityResultLauncher<Intent> cameraLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Initialize UI and layout immediately
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        Button btnCapture = findViewById(R.id.btn_capture);

        // 2. Setup Camera Result Handler
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                        saveImageToFile(photo);
                    }
                });

        // 3. Prepare Folder and Load Initial Data
        targetFolder = getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES);
        loadImagesFromFolder();

        // 4. Set Listener
        btnCapture.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraLauncher.launch(intent);
        });
    }

    private void saveImageToFile(Bitmap bitmap) {
        File file = new File(targetFolder, "IMG_" + System.currentTimeMillis() + ".jpg");
        try (FileOutputStream out = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            loadImagesFromFolder();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void loadImagesFromFolder() {
        File[] files = targetFolder.listFiles();
        if (files != null) {
            imageFiles.clear();
            imageFiles.addAll(Arrays.asList(files));
            if (adapter == null) {
                adapter = new ImageAdapter(this, imageFiles, file -> {
                    Intent intent = new Intent(this, DetailActivity.class);
                    intent.putExtra("path", file.getAbsolutePath());
                    startActivity(intent);
                });
                recyclerView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadImagesFromFolder();
    }
}