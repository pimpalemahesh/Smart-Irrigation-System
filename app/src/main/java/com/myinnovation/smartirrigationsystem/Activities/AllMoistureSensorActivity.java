package com.myinnovation.smartirrigationsystem.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myinnovation.smartirrigationsystem.Adapter.MoistureSensorAdapter;
import com.myinnovation.smartirrigationsystem.Modals.MoistureSensorModel;
import com.myinnovation.smartirrigationsystem.databinding.ActivityAllMoistureSensorBinding;

import java.util.ArrayList;
import java.util.Objects;

public class AllMoistureSensorActivity extends AppCompatActivity {

    ActivityAllMoistureSensorBinding binding;
    private ArrayList<MoistureSensorModel> List = new ArrayList<>();
    private MoistureSensorAdapter adapter;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllMoistureSensorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        refreshPage();

        binding.addSensor.setOnClickListener(v -> startActivity(new Intent(AllMoistureSensorActivity.this, AddMoistureSensorActivity.class)));

    }

    private void refreshPage(){
        reference.child("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("SENSORS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List.clear();
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        List.add(dataSnapshot.getValue(MoistureSensorModel.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter = new MoistureSensorAdapter(List, getApplicationContext());
        binding.allSensorRclv.setAdapter(adapter);
        binding.allSensorRclv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.allSensorRclv.setHasFixedSize(true);

        refreshThread();
    }

    private void refreshThread() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                refreshPage();
            }
        };
        handler.postDelayed(runnable, 1000);
    }
}