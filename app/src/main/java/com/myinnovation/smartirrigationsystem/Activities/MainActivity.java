package com.myinnovation.smartirrigationsystem.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.myinnovation.smartirrigationsystem.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        binding.toSoilMoistureSensorActivity.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MoistureSensorActivity.class)));
        binding.toTemperatureSensorActivity.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TemperatureSensorActivity.class)));
        binding.toMotorActivity.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MotorActivity.class)));
        binding.setting.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SettingActivity.class)));
    }



    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }
}