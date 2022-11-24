package com.myinnovation.smartirrigationsystem.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myinnovation.smartirrigationsystem.Adapter.NotificationAdapter;
import com.myinnovation.smartirrigationsystem.Modals.Notification;
import com.myinnovation.smartirrigationsystem.databinding.ActivityNotificationBinding;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    ActivityNotificationBinding binding;
    ArrayList<Notification> List = new ArrayList<>();

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List.add(new Notification("101", "Soil moisture at sensor 101 is reduced to great extent motor is started", "WARNING"));
        List.add(new Notification("102", "Sensor 103 is switch offed check it!", "WARNING"));
        List.add(new Notification("103", "Motor started.", "SIMPLE"));
        List.add(new Notification("104", "Motor stopped.", "DANGER"));

        NotificationAdapter adapter = new NotificationAdapter(List, getApplicationContext());
        binding.notificationRclv.setAdapter(adapter);
        binding.notificationRclv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.notificationRclv.setHasFixedSize(true);
        binding.notificationRclv.setNestedScrollingEnabled(false);

    }

    private class FirebaseThread extends Thread{

        @Override
        public void run() {
            reference.child("Users").child(mAuth.getUid()).child("Notification").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Toast.makeText(getApplicationContext(), dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                            List.add(dataSnapshot.getValue(Notification.class));
                        }
                    } else{
                        Toast.makeText(getApplicationContext(), "No Notifications", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    List.add(new Notification("404", error.getMessage(), "DANGER"));
                }
            });

            if(List.size() == 0){
                List.add(new Notification("11011", "No Notifications", "SIMPLE"));
            }
            Toast.makeText(getApplicationContext(), String.valueOf(List.size()), Toast.LENGTH_SHORT).show();

            NotificationAdapter adapter = new NotificationAdapter(List, getApplicationContext());
            binding.notificationRclv.setAdapter(adapter);
            binding.notificationRclv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            binding.notificationRclv.setHasFixedSize(true);
            binding.notificationRclv.setNestedScrollingEnabled(false);
        }
    }
}