package com.myinnovation.smartirrigationsystem.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myinnovation.smartirrigationsystem.Activities.MotorActivity;
import com.myinnovation.smartirrigationsystem.Modals.MoistureSensorModel;
import com.myinnovation.smartirrigationsystem.Modals.Notification;
import com.myinnovation.smartirrigationsystem.R;
import com.myinnovation.smartirrigationsystem.databinding.SingleNotificationLayoutBinding;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>{
    private final ArrayList<Notification> List;
    private final Context context;

    public NotificationAdapter(ArrayList<Notification> list, Context context) {
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
        Notification notification = List.get(position);
        holder.binding.notificationText.setText(notification.getnText());
        if(notification.getnType().equals("WARNING")){
            holder.binding.notificationText.setTextColor(context.getResources().getColor(R.color.yellow));
        } else if(notification.getnType().equals("DANGER")){
            holder.binding.notificationText.setTextColor(context.getResources().getColor(R.color.red));
        } else{

        }
    }

    @Override
    public int getItemCount() {
        return List.size();
    }


    public static class NotificationViewHolder extends RecyclerView.ViewHolder{
        SingleNotificationLayoutBinding binding;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SingleNotificationLayoutBinding.bind(itemView);
        }
    }
}
