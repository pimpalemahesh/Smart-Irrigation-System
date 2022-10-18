package com.myinnovation.smartirrigationsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.myinnovation.smartirrigationsystem.Adapter.MoistureSensorAdapter;
import com.myinnovation.smartirrigationsystem.Modals.MoistureSensorModel;
import com.myinnovation.smartirrigationsystem.databinding.ActivityAllMoistureSensorBinding;

import java.util.ArrayList;

public class AllMoistureSensorActivity extends AppCompatActivity {

    ActivityAllMoistureSensorBinding binding;
    private ArrayList<MoistureSensorModel> List;
    private MoistureSensorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllMoistureSensorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List = new ArrayList<>();
        List.add(new MoistureSensorModel("101", "23", true));
        List.add(new MoistureSensorModel("102", "18", true));
        List.add(new MoistureSensorModel("103", "22", false));
        List.add(new MoistureSensorModel("104", "20", true));
        List.add(new MoistureSensorModel("105", "14", true));
        List.add(new MoistureSensorModel("106", "12", true));
        List.add(new MoistureSensorModel("107", "11", false));
        List.add(new MoistureSensorModel("108", "17", true));

        adapter = new MoistureSensorAdapter(List, getApplicationContext());
        binding.allSensorRclv.setAdapter(adapter);
        binding.allSensorRclv.setLayoutManager(new LinearLayoutManager(this));
        binding.allSensorRclv.setHasFixedSize(true);

    }
}