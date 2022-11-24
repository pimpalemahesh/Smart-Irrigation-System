package com.myinnovation.smartirrigationsystem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.myinnovation.smartirrigationsystem.databinding.ActivityRegisterBinding;


public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signup.setOnClickListener(v -> validateFieldsForSignUp());
    }

    private void validateFieldsForSignUp() {

        if (TextUtils.isEmpty(binding.mobNum.getText().toString())) {
            binding.mobNum.setError("Required");
            return;
        } else if (!Patterns.PHONE.matcher(binding.mobNum.getText().toString()).matches()) {
            Toast.makeText(this, "Wrong Mobile Number", Toast.LENGTH_LONG).show();
            return;
        } else if (binding.mobNum.getText().toString().length() != 10) {
            Toast.makeText(this, "Mobile Length should be 10 only.", Toast.LENGTH_LONG).show();
            return;
        }

        registerUser();
    }

    private void registerUser() {
        Intent intent = new Intent(RegisterActivity.this, OTPVerificationActivity.class);
        intent.putExtra("USERNAME", binding.name.getText().toString());
        intent.putExtra("MOBILE", binding.mobNum.getText().toString());
        startActivity(intent);
    }
}