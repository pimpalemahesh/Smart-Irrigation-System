package com.myinnovation.smartirrigationsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.myinnovation.smartirrigationsystem.R;
import com.myinnovation.smartirrigationsystem.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toSoilMoistureSensorActivity.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MoistureSensorActivity.class)));
        binding.toTemperatureSensorActivity.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TemperatureSensorActivity.class)));
        binding.toMotorActivity.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MotorActivity.class)));

    }
}