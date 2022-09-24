package com.myinnovation.smartirrigationsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.myinnovation.smartirrigationsystem.databinding.ActivityWeatherBinding;

public class WeatherActivity extends AppCompatActivity {

    ActivityWeatherBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWeatherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}