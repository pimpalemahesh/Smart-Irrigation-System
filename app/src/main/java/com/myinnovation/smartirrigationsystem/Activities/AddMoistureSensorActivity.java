package com.myinnovation.smartirrigationsystem.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myinnovation.smartirrigationsystem.Modals.MoistureSensorModel;
import com.myinnovation.smartirrigationsystem.databinding.ActivityAddMoistureSensorBinding;


public class AddMoistureSensorActivity extends AppCompatActivity {

    ActivityAddMoistureSensorBinding binding;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMoistureSensorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.floatingActionButton2.setOnClickListener(v -> {
            if(binding.editTextNumber.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Enter New Sensor Id", Toast.LENGTH_SHORT).show();
                binding.editTextNumber.setError("Required");
                return;
            } else{
                binding.bar.setVisibility(View.VISIBLE);
                MoistureSensorModel sensor = new MoistureSensorModel(binding.editTextNumber.getText().toString(), 0, false);
                reference.child("Users").child(FirebaseAuth.getInstance().getUid()).child("SENSORS").child(sensor.getSensorId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            ShowToast("Sensor with this Id is already exist please select another Id!");
                            binding.bar.setVisibility(View.INVISIBLE);
                        } else{
                            reference.child("Users").child(FirebaseAuth.getInstance().getUid()).child("SENSORS").child(sensor.getSensorId()).setValue(sensor).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    ShowToast("New Sensor Added Successfully!");
                                    binding.bar.setVisibility(View.INVISIBLE);
                                    startActivity(new Intent(getApplicationContext(), AllMoistureSensorActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    ShowToast("Failed to create new Sensor " + e.getMessage());
                                    binding.bar.setVisibility(View.INVISIBLE);
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void ShowToast(String str){
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
    }
}