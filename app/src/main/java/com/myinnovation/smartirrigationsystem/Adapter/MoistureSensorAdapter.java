package com.myinnovation.smartirrigationsystem.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myinnovation.smartirrigationsystem.Activities.MotorActivity;
import com.myinnovation.smartirrigationsystem.Modals.MoistureSensorModel;
import com.myinnovation.smartirrigationsystem.R;
import com.myinnovation.smartirrigationsystem.databinding.SingleMoistureSensorLayoutBinding;

import java.util.ArrayList;

public class MoistureSensorAdapter extends RecyclerView.Adapter<MoistureSensorAdapter.MoistureSensorViewHolder>{
    private final ArrayList<MoistureSensorModel> List;
    private final Context context;

    public MoistureSensorAdapter(ArrayList<MoistureSensorModel> list, Context context) {
        List = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MoistureSensorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoistureSensorViewHolder(LayoutInflater.from(context).inflate(R.layout.single_moisture_sensor_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MoistureSensorViewHolder holder, int position) {
        MoistureSensorModel sensor = List.get(position);
        holder.binding.sensorId.setText(sensor.getSensorId());
        holder.binding.sensorValue.setText(String.valueOf(sensor.getSensorValue()));
        if(sensor.getState() != null){
            if(sensor.getState() && sensor.getSensorValue() < 15){
                holder.binding.sensorState.setImageResource(R.drawable.sensor_on);
                holder.binding.cardView3.setCardBackgroundColor(context.getResources().getColor(R.color.yellow));
            } else if(sensor.getState()){
                holder.binding.sensorState.setImageResource(R.drawable.sensor_on);
                holder.binding.cardView3.setCardBackgroundColor(context.getResources().getColor(R.color.green));
            }
            else{
                holder.binding.sensorState.setImageResource(R.drawable.sensor_off);
                holder.binding.sensorValue.setText("0");
                holder.binding.cardView3.setCardBackgroundColor(context.getResources().getColor(R.color.red));
            }
            if(sensor.getState()){
                holder.itemView.setOnClickListener(v -> {
                    context.startActivity(new Intent(context, MotorActivity.class).putExtra("SID", sensor.getSensorId()));
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public static class MoistureSensorViewHolder extends RecyclerView.ViewHolder{
        SingleMoistureSensorLayoutBinding binding;
        public MoistureSensorViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SingleMoistureSensorLayoutBinding.bind(itemView);
        }
    }
}
