package com.myinnovation.smartirrigationsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.google.android.material.slider.Slider;
import com.myinnovation.smartirrigationsystem.Utitlity.LuncherManager;
import com.myinnovation.smartirrigationsystem.databinding.ActivitySplashScreenBinding;

import java.util.Locale;

public class SplashScreen extends AppCompatActivity {

    ActivitySplashScreenBinding binding;
    LuncherManager luncherManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SharedPreferences preferences = getSharedPreferences("SETTINGS", MODE_PRIVATE);
        if (preferences.contains("LANG")) {
            loadLocale();
            setLocale(preferences.getString("LANG", ""));
        }

        luncherManager = new LuncherManager(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (luncherManager.isFirstTime()) {
                    luncherManager.setFirstLunch(false);
                    startActivity(new Intent(getApplicationContext(), SliderActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }

            }
        }, 2000);
    }

    private void loadLocale() {
        SharedPreferences preferences = getSharedPreferences("SETTINGS", MODE_PRIVATE);
        String language = preferences.getString("LANG", "");
        setLocale(language);
    }

    private void setLocale(String language) {
        Locale locale = new Locale(language, "IN");
        Locale.setDefault(locale);
        Configuration config = this.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            createConfigurationContext(config);
        }
        this.getResources().updateConfiguration(config, this.getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("SETTINGS", MODE_PRIVATE).edit();
        editor.putString("LANG", language);
        editor.apply();
    }
}