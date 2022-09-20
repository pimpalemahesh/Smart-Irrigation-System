package com.myinnovation.smartirrigationsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.myinnovation.smartirrigationsystem.R;
import com.myinnovation.smartirrigationsystem.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements SensorEventListener, AdapterView.OnItemSelectedListener {

    ActivityMainBinding binding;
    SensorManager sensorManager;
    private Sensor sensor;
    private Boolean isSensorAvailable = false;
    private String selectedLanguage = "English";
    private ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if(selectedLanguage.equals("English")){
             adapter = ArrayAdapter.createFromResource(this, R.array.English_Languages, android.R.layout.simple_spinner_item);
        } else{
            adapter = ArrayAdapter.createFromResource(this, R.array.Marathi_Language, android.R.layout.simple_spinner_item);
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.languageSpinner.setAdapter(adapter);

        binding.languageSpinner.setOnItemSelectedListener(this);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        binding.toSoilMoistureSensorActivity.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MoistureSensorActivity.class)));
        binding.toTemperatureSensorActivity.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TemperatureSensorActivity.class)));
        binding.toMotorActivity.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MotorActivity.class)));

        if(sensorManager.getDefaultSensor(sensor.TYPE_AMBIENT_TEMPERATURE) != null){
            sensor = sensorManager.getDefaultSensor(sensor.TYPE_AMBIENT_TEMPERATURE);
            isSensorAvailable = true;
        } else{
            binding.temperature.setText("?");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        binding.temperature.setText(event.values[0] + "");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isSensorAvailable){
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isSensorAvailable){
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedLanguage = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}