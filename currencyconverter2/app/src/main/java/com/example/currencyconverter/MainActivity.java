package com.example.currencyconverter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

    // 1. Setup the variables
    EditText etAmount;
    Spinner spinnerFrom, spinnerTo;
    Button btnConvert, btnSettings;
    TextView tvResult;

    // Fixed Rates (USD as base: 1 USD = 83 INR, 150 JPY, 0.92 EUR)
    String[] currencies = {"USD", "INR", "JPY", "EUR"};
    double[] rates = {1.0, 83.0, 150.0, 0.92};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Theme must be applied before setContentView
        checkTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2. Link Java to XML IDs
        etAmount = findViewById(R.id.et_amount);
        spinnerFrom = findViewById(R.id.spinner_from);
        spinnerTo = findViewById(R.id.spinner_to);
        btnConvert = findViewById(R.id.btn_convert);
        tvResult = findViewById(R.id.tv_result);
        btnSettings = findViewById(R.id.btn_settings);

        // 3. Setup Dropdowns
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, currencies);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        // 4. Click Listener for Convert
        btnConvert.setOnClickListener(v -> {
            String val = etAmount.getText().toString();
            if (!val.isEmpty()) {
                double amount = Double.parseDouble(val);
                int from = spinnerFrom.getSelectedItemPosition();
                int to = spinnerTo.getSelectedItemPosition();

                // Logic: Convert input to USD, then USD to target
                double inUsd = amount / rates[from];
                double result = inUsd * rates[to];

                tvResult.setText(String.format("Result: %.2f %s", result, currencies[to]));
            } else {
                Toast.makeText(this, "Enter an amount", Toast.LENGTH_SHORT).show();
            }
        });

        // 5. Open Settings
        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    private void checkTheme() {
        SharedPreferences pref = getSharedPreferences("Settings", MODE_PRIVATE);
        boolean dark = pref.getBoolean("DarkMode", false);
        if (dark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}