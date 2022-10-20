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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMotorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent() != null) {
            sensorId = getIntent().getStringExtra("SID");
            binding.sid.setText(sensorId);
        }

        reference.child("MOTOR").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    reference.child("MOTOR").child("state").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                motorState = snapshot.getValue(Boolean.class);
                                if (!motorState) {
                                    binding.motorSpeedSeekbar.setEnabled(false);
                                    binding.motorSpeedSeekbar.setProgress(0);
                                    binding.buttonState.setImageDrawable(getResources().getDrawable(R.drawable.sensor_off));
                                } else {
                                    binding.motorSpeedSeekbar.setEnabled(true);
                                    binding.buttonState.setImageDrawable(getResources().getDrawable(R.drawable.sensor_on));
                                    reference.child("MOTOR").child("time").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if(snapshot.exists()){
                                                String motorTime = snapshot.getValue(String.class);
                                                binding.motorWorkingTime.setText(motorTime);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }

                                reference.child("MOTOR").child("speed").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()){
                                            binding.motorSpeedSeekbar.setProgress(snapshot.getValue(Integer.class));
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                binding.motorSpeedSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                        binding.motorSpeed.setText(progress + "");
                                    }

                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {

                                    }

                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {
                                        reference.child("MOTOR").child("speed").setValue(seekBar.getProgress()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                showToast("Speed changed");
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                showToast("Failed to change speed");
                                            }
                                        });
                                    }
                                });

                                binding.motorOn.setOnClickListener(v -> {
                                    if (motorState) {
                                        return;
                                    } else {
                                        reference.child("MOTOR").child("state").setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                binding.motorSpeedSeekbar.setEnabled(true);
                                                binding.buttonState.setImageDrawable(getResources().getDrawable(R.drawable.sensor_on));

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
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
                                    } else{
                                        reference.child("MOTOR").child("state").setValue(false).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                reference.child("MOTOR").child("speed").setValue(0).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        reference.child("MOTOR").child("time").setValue("00").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                binding.motorSpeedSeekbar.setEnabled(false);
                                                                binding.motorSpeedSeekbar.setProgress(0);
                                                                binding.motorWorkingTime.setText("00:00");
                                                                binding.motorSpeed.setText("0");
                                                            }
                                                        });
                                                    }
                                                });
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

    private void showToast(String str){
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
    }
}