package com.myinnovation.smartirrigationsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.myinnovation.smartirrigationsystem.databinding.ActivityMotorBinding;
import com.myinnovation.smartirrigationsystem.databinding.ActivityTemperatureSensorBinding;

public class TemperatureSensorActivity extends AppCompatActivity {

    ActivityTemperatureSensorBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTemperatureSensorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}