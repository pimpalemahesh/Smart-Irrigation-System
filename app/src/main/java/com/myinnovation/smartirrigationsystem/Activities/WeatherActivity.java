package com.myinnovation.smartirrigationsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.myinnovation.smartirrigationsystem.R;
import com.myinnovation.smartirrigationsystem.databinding.ActivityWeatherBinding;

public class WeatherActivity extends AppCompatActivity {

    ActivityWeatherBinding binding;
    String weather_report = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWeatherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(getIntent() != null){
            weather_report = getIntent().getStringExtra("WR");
            binding.weatherReport.setText(weather_report);
        } else{
            binding.weatherReport.setText(R.string.no_report);
        }
    }
}