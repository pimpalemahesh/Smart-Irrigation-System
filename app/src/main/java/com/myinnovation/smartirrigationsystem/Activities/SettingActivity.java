package com.myinnovation.smartirrigationsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.myinnovation.smartirrigationsystem.R;
import com.myinnovation.smartirrigationsystem.databinding.ActivitySettingBinding;

import java.util.Locale;

public class SettingActivity extends AppCompatActivity {

    ActivitySettingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
        });
        binding.language.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.english:
                    setLocale("en");
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    break;

                case R.id.marathi:
                    setLocale("mr");
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    break;
            }
        });
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