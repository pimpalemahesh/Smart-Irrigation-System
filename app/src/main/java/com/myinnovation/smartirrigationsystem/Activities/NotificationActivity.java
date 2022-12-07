package com.myinnovation.smartirrigationsystem.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myinnovation.smartirrigationsystem.Adapter.NotificationAdapter;
import com.myinnovation.smartirrigationsystem.Modals.NotificationModal;
import com.myinnovation.smartirrigationsystem.databinding.ActivityNotificationBinding;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    ActivityNotificationBinding binding;
    ArrayList<NotificationModal> List = new ArrayList<>();

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    NotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        refreshPage();

    }

    private void refreshPage() {
        reference.child("Notification").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        List.add(dataSnapshot.getValue(NotificationModal.class));
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter = new NotificationAdapter(List, getApplicationContext());
        binding.notificationRclv.setAdapter(adapter);
        binding.notificationRclv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.notificationRclv.setHasFixedSize(true);

        refreshThread();
    }

    private void refreshThread() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {

                refreshPage();
            }
        };
        handler.postDelayed(runnable, 1000);
    }
}