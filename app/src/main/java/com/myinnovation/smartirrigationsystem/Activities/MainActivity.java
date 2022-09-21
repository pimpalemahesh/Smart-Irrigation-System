package com.myinnovation.smartirrigationsystem.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.myinnovation.smartirrigationsystem.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // shared preference may not available
//        loadLocale();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        binding.selectLanguage.setOnClickListener(v -> {
            changeLanguage();
        });

        binding.toSoilMoistureSensorActivity.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MoistureSensorActivity.class)));
        binding.toTemperatureSensorActivity.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TemperatureSensorActivity.class)));
        binding.toMotorActivity.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MotorActivity.class)));

    }

    private void changeLanguage(){
        final String[] languages = {"English", "Marathi"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setSingleChoiceItems(languages, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    setLocale("en");
                } else{
                    setLocale("mr");
                }
                recreate();
            }
        });
        mBuilder.create();
        mBuilder.show();
    }


    private void setLocale(String language){
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("SETTINGS", MODE_PRIVATE).edit();
        editor.putString("LANG", language);
        editor.apply();
    }

    private void loadLocale(){
            SharedPreferences preferences = getSharedPreferences("SETTINGS", MODE_PRIVATE);
            String language = preferences.getString("LANG", "en");
            setLocale(language);
    }


    private void showToast(String str){
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }
}