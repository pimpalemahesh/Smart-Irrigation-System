package com.myinnovation.smartirrigationsystem.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myinnovation.smartirrigationsystem.R;
import com.myinnovation.smartirrigationsystem.databinding.ActivityMotorBinding;
import com.myinnovation.smartirrigationsystem.databinding.ActivityTemperatureSensorBinding;

public class TemperatureSensorActivity extends AppCompatActivity {

    ActivityTemperatureSensorBinding binding;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTemperatureSensorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        refreshPage();
    }

    private void refreshPage(){
        reference.child("Temperature").child("state")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            if(snapshot.getValue(Boolean.class)){
                                binding.temperatureState.setImageDrawable(getResources().getDrawable(R.drawable.sensor_on));
                            } else{
                                binding.temperatureState.setImageDrawable(getResources().getDrawable(R.drawable.sensor_off));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        reference.child("Temperature").child("Value").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    binding.tempValue.setText(snapshot.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.child("Humidity").child("Value").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    binding.humidity.setText(snapshot.getValue(String.class) + "%");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        refreshThread();
    }

    private void refreshThread(){
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                refreshPage();
            }
        };
        handler.postDelayed(runnable, 1000);
    }
}