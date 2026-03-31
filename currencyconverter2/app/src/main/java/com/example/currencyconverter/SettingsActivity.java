package com.example.currencyconverter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Link the switch and button from the XML
        Switch swDarkMode = findViewById(R.id.sw_dark_mode);
        Button btnBack = findViewById(R.id.btn_back);

        // Load current theme state (Light or Dark)
        SharedPreferences pref = getSharedPreferences("Settings", MODE_PRIVATE);
        boolean isDark = pref.getBoolean("DarkMode", false);
        swDarkMode.setChecked(isDark);

        // Logic for when you toggle the switch
        swDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("DarkMode", isChecked);
            editor.apply();

            // Change theme immediately
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        // Logic for the Back button
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}