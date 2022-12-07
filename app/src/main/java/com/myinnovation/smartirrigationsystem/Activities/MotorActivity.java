package com.myinnovation.smartirrigationsystem.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myinnovation.smartirrigationsystem.R;
import com.myinnovation.smartirrigationsystem.databinding.ActivityMotorBinding;

import java.util.Locale;


public class MotorActivity extends AppCompatActivity {

    ActivityMotorBinding binding;
    private String sensorId = "NULL";
    private boolean motorState = false;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String uid = mAuth.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMotorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent() != null) {
            sensorId = getIntent().getStringExtra("SID");
            binding.sid.setText(sensorId);
        }

        reference.child("Users").child(uid).child("MOTOR")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            reference.child("Users").child(uid).child("MOTOR").child("state")
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                motorState = snapshot.getValue(Boolean.class);
                                                if (!motorState) {
                                                    binding.buttonState.setImageDrawable(getResources().getDrawable(R.drawable.sensor_off));
                                                } else {
                                                    binding.buttonState.setImageDrawable(getResources().getDrawable(R.drawable.sensor_on));
                                                    reference.child("Users").child(uid).child("MOTOR").child("time")
                                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    if (snapshot.exists()) {
                                                                        String motorTime = snapshot.getValue(String.class);
                                                                        binding.motorWorkingTime.setText(motorTime);
                                                                    }
                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                }

                                                binding.motorOn.setOnClickListener(v -> {
                                                    if (motorState) {
                                                        return;
                                                    } else {
                                                        reference.child("Users").child(uid).child("MOTOR").child("state").setValue(true)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void unused) {
                                                                        binding.buttonState.setImageDrawable(getResources().getDrawable(R.drawable.sensor_on));

                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        showToast("Failed to change state of motor");
                                                                    }
                                                                });
                                                    }
                                                });


                                                binding.motorOff.setOnClickListener(v -> {
                                                    if (!motorState) {
                                                        return;
                                                    } else {
                                                        reference.child("Users").child(uid).child("MOTOR").child("state").setValue(false).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                reference.child("Users").child(uid).child("MOTOR").child("time").setValue("00");
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                showToast(e.getLocalizedMessage().toString());
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }

    private void showToast(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
    }
}