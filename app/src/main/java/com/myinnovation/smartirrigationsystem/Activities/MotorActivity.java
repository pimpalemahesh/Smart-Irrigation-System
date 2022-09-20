package com.myinnovation.smartirrigationsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.myinnovation.smartirrigationsystem.R;
import com.myinnovation.smartirrigationsystem.databinding.ActivityMotorBinding;


public class MotorActivity extends AppCompatActivity {

    ActivityMotorBinding binding;
    private boolean motorState = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMotorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.motorSpeedSeekbar.setProgress(Integer.parseInt(binding.motorSpeed.getText().toString()));
        if(motorState){
            binding.motorSpeedSeekbar.setEnabled(true);
        } else{
            binding.motorSpeedSeekbar.setEnabled(false);
        }
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

            }
        });
        binding.motorOn.setOnClickListener(v -> {
            if(motorState){
                return;
            }
            motorState = true;
            binding.motorSpeedSeekbar.setEnabled(true);
            if(motorState){
                binding.buttonState.setImageDrawable(getResources().getDrawable(R.drawable.sensor_on));
            } else{
                binding.buttonState.setImageDrawable(getResources().getDrawable(R.drawable.sensor_off));
            }
        });

        binding.motorOff.setOnClickListener(v -> {
            if(!motorState){
                return;
            }
            motorState = false;
            binding.motorSpeedSeekbar.setEnabled(false);
            binding.motorSpeedSeekbar.setProgress(0);
            binding.motorSpeed.setText("0");
            if(!motorState){
                binding.buttonState.setImageDrawable(getResources().getDrawable(R.drawable.sensor_off));
            } else{
                binding.buttonState.setImageDrawable(getResources().getDrawable(R.drawable.sensor_on));
            }
        });

    }
}