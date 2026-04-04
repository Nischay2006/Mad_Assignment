package com.example.photogallery;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        String path = getIntent().getStringExtra("path");
        File file = new File(path);

        ImageView img = findViewById(R.id.imgDetail);
        TextView txtInfo = findViewById(R.id.txtInfo);
        Button btnDelete = findViewById(R.id.btnDelete);

        img.setImageURI(Uri.fromFile(file));

        // Image Details [cite: 48]
        txtInfo.setText("Name: " + file.getName() +
                "\nPath: " + file.getAbsolutePath() +
                "\nSize: " + (file.length() / 1024) + " KB" +
                "\nDate: " + new Date(file.lastModified()));

        // Delete with Confirmation Dialog Requirement [cite: 49]
        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Image")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Delete", (d, w) -> {
                        if (file.delete()) {
                            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }
}