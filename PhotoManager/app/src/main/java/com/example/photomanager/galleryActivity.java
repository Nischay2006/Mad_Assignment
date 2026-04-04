package com.example.photomanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String path = getIntent().getStringExtra("path");
        File file = new File(path);

        ImageView img = findViewById(R.id.detail_image);
        TextView details = findViewById(R.id.txt_details);
        Button delete = findViewById(R.id.btn_delete);

        img.setImageURI(android.net.Uri.fromFile(file));
        details.setText("Name: " + file.getName() + "\nSize: " + (file.length()/1024) + " KB\nDate: " + new Date(file.lastModified()));

        delete.setOnClickListener(v -> {
            new AlertDialog.Builder(this).setTitle("Delete?").setMessage("Are you sure?").setPositiveButton("Yes", (d, w) -> {
                if(file.delete()) finish();
            }).setNegativeButton("No", null).show();
        });
    }
}