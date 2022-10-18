package com.myinnovation.smartirrigationsystem.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.myinnovation.smartirrigationsystem.databinding.ActivityMoistureSensorInfoBinding;

public class MoistureSensorInfoActivity extends AppCompatActivity {

    ActivityMoistureSensorInfoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMoistureSensorInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}