package com.myinnovation.smartirrigationsystem.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myinnovation.smartirrigationsystem.Adapter.NotificationAdapter;
import com.myinnovation.smartirrigationsystem.Modals.Notification;
import com.myinnovation.smartirrigationsystem.databinding.ActivityMyNotificationBinding;
import com.myinnovation.smartirrigationsystem.databinding.ActivityNotificationBinding;

import java.util.ArrayList;

public class MyNotificationActivity extends AppCompatActivity {

    ActivityMyNotificationBinding binding;
    ArrayList<Notification> List = new ArrayList<>();

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        reference.child("Users").child(mAuth.getUid()).child("Notification").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        List.add(dataSnapshot.getValue(Notification.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                List.add(new Notification("404", error.getMessage(), "DANGER"));
            }
        });

        NotificationAdapter adapter = new NotificationAdapter(List, getApplicationContext());
        binding.notificationRclv.setAdapter(adapter);
        binding.notificationRclv.setLayoutManager(new LinearLayoutManager(this));
        binding.notificationRclv.setHasFixedSize(true);
        binding.notificationRclv.setNestedScrollingEnabled(false);

    }
}