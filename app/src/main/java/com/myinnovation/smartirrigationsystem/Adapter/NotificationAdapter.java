package com.myinnovation.smartirrigationsystem.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myinnovation.smartirrigationsystem.Modals.NotificationModal;
import com.myinnovation.smartirrigationsystem.R;
import com.myinnovation.smartirrigationsystem.databinding.SingleNotificationLayoutBinding;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private final ArrayList<NotificationModal> List;
    private final Context context;

    public NotificationAdapter(ArrayList<NotificationModal> list, Context context) {
        List = list;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationAdapter.NotificationViewHolder(LayoutInflater.from(context).inflate(R.layout.single_notification_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotificationViewHolder holder, int position) {
        NotificationModal notificationModal = List.get(position);
        holder.binding.notificationText.setText(notificationModal.getnText());
        if (notificationModal.getnType().equals("WARNING")) {
            holder.binding.notificationText.setTextColor(context.getResources().getColor(R.color.yellow));
        } else if (notificationModal.getnType().equals("DANGER")) {
            holder.binding.notificationText.setTextColor(context.getResources().getColor(R.color.red));
        }

        holder.itemView.setOnClickListener(v -> {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
            alertbox.setMessage("Do you want to delete it");
            alertbox.setTitle("Warning");
            alertbox.setIcon(R.drawable.black_notification);

            alertbox.setNeutralButton("OK",
                    (arg0, arg1) -> FirebaseDatabase.getInstance().getReference().child("Notification").child(notificationModal.getnId()).setValue(null)
                            .addOnSuccessListener(unused -> FirebaseDatabase.getInstance().getReference().child("NotificationCount")
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                String count = snapshot.getValue(String.class);
                                                int c = Integer.parseInt(count) - 1;
                                                FirebaseDatabase.getInstance().getReference().child("NotificationCount").setValue(String.valueOf(c))
                                                        .addOnSuccessListener(unused1 -> showToast("Successfully deleted"));
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    }))
                            .addOnFailureListener(e -> showToast(e.getMessage())));
            alertbox.show();
        });
    }

    private void showToast(String str){
        Toast.makeText(context.getApplicationContext(), str, Toast.LENGTH_LONG).show();
    }

    @Override
    public int getItemCount() {
        return List.size();
    }


    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        SingleNotificationLayoutBinding binding;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SingleNotificationLayoutBinding.bind(itemView);
        }
    }
}
