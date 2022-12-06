package com.myinnovation.smartirrigationsystem.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.tracing.FirebaseTrace;
import com.myinnovation.smartirrigationsystem.Adapter.MoistureSensorAdapter;
import com.myinnovation.smartirrigationsystem.Adapter.NotificationAdapter;
import com.myinnovation.smartirrigationsystem.Modals.MoistureSensorModel;
import com.myinnovation.smartirrigationsystem.Modals.Notification;
import com.myinnovation.smartirrigationsystem.databinding.ActivityNotificationBinding;

import java.util.ArrayList;
import java.util.Objects;

public class NotificationActivity extends AppCompatActivity {

    ActivityNotificationBinding binding;
    ArrayList<Notification> List = new ArrayList<>();

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

//    private class FirebaseThread extends Thread{
//
//        @Override
//        public void run() {
//            reference.child("Notification").addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if (snapshot.exists()) {
//                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                            List.add(dataSnapshot.getValue(Notification.class));
//                        }
//                    } else{
//                        Toast.makeText(getApplicationContext(), "No Notifications", Toast.LENGTH_LONG).show();
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
//                    List.add(new Notification("404", error.getMessage(), "DANGER"));
//                }
//            });
//
//            if(List.size() == 0){
//                List.add(new Notification("11011", "No Notifications", "SIMPLE"));
//            }
//
//            NotificationAdapter adapter = new NotificationAdapter(List, getApplicationContext());
//            binding.notificationRclv.setAdapter(adapter);
//            binding.notificationRclv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//            binding.notificationRclv.setHasFixedSize(true);
//            binding.notificationRclv.setNestedScrollingEnabled(false);
//        }
//    }

    private void refreshPage() {
        reference.child("Notification").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        List.add(dataSnapshot.getValue(Notification.class));
                        Toast.makeText(NotificationActivity.this, dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    List.add(new Notification("11011", "No Notifications", "SIMPLE"));
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
                refreshThread();
            }
        };
        handler.postDelayed(runnable, 1000);
    }
}