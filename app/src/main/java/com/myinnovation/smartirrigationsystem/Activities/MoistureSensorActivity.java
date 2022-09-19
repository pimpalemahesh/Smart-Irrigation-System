package com.myinnovation.smartirrigationsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.myinnovation.smartirrigationsystem.R;
import com.myinnovation.smartirrigationsystem.databinding.ActivityMoistureSensorBinding;
import com.myinnovation.smartirrigationsystem.databinding.ActivityMotorBinding;

public class MoistureSensorActivity extends AppCompatActivity {

    ActivityMoistureSensorBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMoistureSensorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}