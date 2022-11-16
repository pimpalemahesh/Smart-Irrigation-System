package com.myinnovation.smartirrigationsystem.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
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
        FirebaseDataThread thread = new FirebaseDataThread();
        thread.start();

        binding.addSensor.setOnClickListener(v -> startActivity(new Intent(AllMoistureSensorActivity.this, AddMoistureSensorActivity.class)));
//        List = new ArrayList<>();
//        List.add(new MoistureSensorModel("101", "23", true));
//        List.add(new MoistureSensorModel("102", "18", true));
//        List.add(new MoistureSensorModel("103", "22", false));
//        List.add(new MoistureSensorModel("104", "20", true));
//        List.add(new MoistureSensorModel("105", "14", true));
//        List.add(new MoistureSensorModel("106", "12", true));
//        List.add(new MoistureSensorModel("107", "11", false));
//        List.add(new MoistureSensorModel("108", "17", true));


    }

    class FirebaseDataThread extends Thread{

        @Override
        public void run() {
            try{
                reference.child("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("SENSORS").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                List.add(dataSnapshot.getValue(MoistureSensorModel.class));
                                adapter = new MoistureSensorAdapter(List, getApplicationContext());
                                binding.allSensorRclv.setAdapter(adapter);
                                binding.allSensorRclv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                binding.allSensorRclv.setHasFixedSize(true);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            } catch (Exception e){
                Toast.makeText(AllMoistureSensorActivity.this, "Error 404 " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }
}