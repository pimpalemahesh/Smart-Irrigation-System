package com.myinnovation.smartirrigationsystem.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.myinnovation.smartirrigationsystem.Modals.User;
import com.myinnovation.smartirrigationsystem.R;
import com.myinnovation.smartirrigationsystem.databinding.ActivityOtpverificationBinding;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OTPVerificationActivity extends AppCompatActivity {

    ActivityOtpverificationBinding binding;

    private String verificationID = "";
    private String username = "", mobile = "";
    private FirebaseAuth mAuth;
    private String userOTP = "";
    private ProgressDialog dialog, pd;

    private FirebaseDatabase mbase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpverificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        InitializeFields();
        sendVerificationCode(mobile);

        binding.otp1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.otp1.getText().toString().length() == 1) {
                    binding.otp2.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        binding.otp2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.otp2.getText().toString().length() == 1) {
                    binding.otp3.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        binding.otp3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.otp3.getText().toString().length() == 1) {
                    binding.otp4.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        binding.otp4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.otp4.getText().toString().length() == 1) {
                    binding.otp5.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        binding.otp5.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.otp5.getText().toString().length() == 1) {
                    binding.otp6.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        binding.otp6.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.otp6.getText().toString().length() == 1) {
                    binding.verifyOtp.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        binding.verifyOtp.setOnClickListener(v -> {
            userOTP = binding.otp1.getText().toString() + binding.otp2.getText().toString() + binding.otp3.getText().toString() + binding.otp4.getText().toString() + binding.otp5.getText().toString() + binding.otp6.getText().toString();
            if (TextUtils.isEmpty(userOTP) || userOTP.length() != 6) {
                Toast.makeText(OTPVerificationActivity.this, "Wrong OTP", Toast.LENGTH_SHORT).show();
            } else
                verifyCode(userOTP.trim());
        });
    }

    private void InitializeFields() {
        mAuth = FirebaseAuth.getInstance();
        mbase = FirebaseDatabase.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setTitle("Please Wait!");
//        dialog.setIcon(R.drawable.ic_warning);
        dialog.setMessage("verification procedure will take some time");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        if (getIntent() != null) {
            username = getIntent().getStringExtra("USERNAME");
            mobile = getIntent().getStringExtra("MOBILE");
        }
    }

    private void sendVerificationCode(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91" + phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
            final String code = credential.getSmsCode();
            if (code != null) {
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(OTPVerificationActivity.this, "Verification Failed " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(@NonNull String s,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(s, token);
            verificationID = s;
            dialog.dismiss();
            Toast.makeText(OTPVerificationActivity.this, "OTP is successfully sent check your inbox", Toast.LENGTH_LONG).show();
            binding.verifyOtp.setEnabled(true);
            binding.bar.setVisibility(View.INVISIBLE);
        }
    };

    private void verifyCode(String Code) {
        binding.bar.setVisibility(View.VISIBLE);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, Code);
        signInByCredentials(credential);
    }

    private void signInByCredentials(PhoneAuthCredential credential) {

        pd = new ProgressDialog(this);
        pd.setTitle("Creating your account!!!");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Wait : ");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener((OnCompleteListener) task -> {
                    if (task.isSuccessful()) {
                        saveUserDetail();
                        binding.bar.setVisibility(View.INVISIBLE);
                        Toast.makeText(OTPVerificationActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OTPVerificationActivity.this, MainActivity.class));
                    }
                })
                .addOnFailureListener(e -> {
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), "Error occurred during creating your account...", Toast.LENGTH_LONG).show();
                });
    } 

    private void saveUserDetail() {
        User user = new User(mAuth.getUid(), username, mobile);

        FirebaseDatabase.getInstance().getReference().child("Users").child("Detail").setValue(user)
                .addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Unable to create user" + e.getMessage(), Toast.LENGTH_LONG).show());

    }

}