package com.myinnovation.smartirrigationsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.myinnovation.smartirrigationsystem.databinding.ActivityMotorBinding;

public class MotorActivity extends AppCompatActivity {

    ActivityMotorBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMotorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}