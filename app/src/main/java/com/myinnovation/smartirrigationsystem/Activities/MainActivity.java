package com.myinnovation.smartirrigationsystem.Activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myinnovation.smartirrigationsystem.R;
import com.myinnovation.smartirrigationsystem.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private long pressedTime;

    RequestQueue requestQueue;
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "fb37a00bc8754c550d30cb541291f716";
    DecimalFormat df = new DecimalFormat("#.##");

    private String weatherData = "";
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        weatherThread thread = new weatherThread();
        thread.start();
        refreshPage();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        reference.child("Notification").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    sendNotification("Visit Notification page to know more about it");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.notificationModal.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, NotificationActivity.class));
        });

        binding.soilMoistureCardView.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MoistureSensorInfoActivity.class)));
        binding.temperatureCardView.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TemperatureSensorActivity.class)));

        binding.toMotorActivity.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AllMoistureSensorActivity.class)));
        binding.setting.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SettingActivity.class)));
        binding.weatherCardView.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, WeatherActivity.class).putExtra("WR", weatherData)));
    }

    private void sendNotification(String str) {
        NotificationManager mNotificationManager;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent ii = new Intent(getApplicationContext().getApplicationContext(), NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, ii, PendingIntent.FLAG_MUTABLE);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.black_notification);
        mBuilder.setContentTitle("You have a notification");
        mBuilder.setContentText(str);

        mNotificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(0, mBuilder.build());

    }

    private class weatherThread extends Thread {

        @Override
        public void run() {
            getWeatherDetails();
        }
    }

    public void getWeatherDetails() {
        String tempUrl = "";
        String city = "Sangli";
        String country = "India";
        if (city.equals("")) {
        } else {
            if (!country.equals("")) {
                tempUrl = url + "?q=" + city + "," + country + "&appid=" + appid;
            } else {
                tempUrl = url + "?q=" + city + "&appid=" + appid;
            }
            StringRequest
                    stringRequest
                    = new StringRequest(
                    Request.Method.GET,
                    tempUrl,
                    new Response.Listener() {
                        @Override
                        public void onResponse(Object response) {
                            try {
                                JSONObject jsonResponse = new JSONObject((String) response);
                                JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                                String description = jsonObjectWeather.getString("description");
                                JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                                double temp = jsonObjectMain.getDouble("temp") - 273.15;
                                double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                                float pressure = jsonObjectMain.getInt("pressure");
                                int humidity = jsonObjectMain.getInt("humidity");
                                JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                                String wind = jsonObjectWind.getString("speed");
                                JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                                String clouds = jsonObjectClouds.getString("all");
                                JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                                String countryName = jsonObjectSys.getString("country");
                                String cityName = jsonResponse.getString("name");
//                                tvResult.setTextColor(Color.rgb(68, 134, 199));
                                weatherData += getResources().getString(R.string.current_weather_of) + " " + cityName + " (" + countryName + ")\n"
                                        + getResources().getString(R.string.temperature) + " " + df.format(temp) + " °C\n"
                                        + getResources().getString(R.string.feels_like) + " " + df.format(feelsLike) + " °C\n"
                                        + getResources().getString(R.string.humidity) + " " + humidity + "%\n"
                                        + getResources().getString(R.string.cloud_description) + " " + description + "\n"
                                        + getResources().getString(R.string.wind_speed) + " " + wind + "m/s (" + getResources().getString(R.string.meters_per_second) + ")\n"
                                        + getResources().getString(R.string.cloudiness) + " " + clouds + "%\n"
                                        + getResources().getString(R.string.pressure) + " " + pressure + " hPa\n";

                                int c = Integer.parseInt(clouds);
                                c = c - 30;
                                binding.temperature.setText(df.format(temp));
                                binding.rain.setText(String.valueOf(c));
                                binding.wind.setText(wind);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            showToast(error.toString() + "\n" + error.getMessage());
                        }
                    });
            requestQueue.add(stringRequest);
        }
    }

    private void refreshPage() {
        reference.child("Temperature").child("State")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            if (snapshot.getValue(Boolean.class)) {
                                binding.temperatureSensorState.setImageDrawable(getResources().getDrawable(R.drawable.sensor_on));
                                reference.child("Temperature").child("Value")
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()) {
                                                    binding.temperatureSensorValue.setText(snapshot.getValue(String.class));
                                                } else {
                                                    binding.temperatureSensorValue.setText("0");
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                reference.child("Humidity").child("Value")
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()) {
                                                    binding.humidityValue.setText(snapshot.getValue(String.class));
                                                } else {
                                                    binding.humidityValue.setText("0");
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                            } else {
                                binding.temperatureSensorState.setImageDrawable(getResources().getDrawable(R.drawable.sensor_off));
                                binding.temperatureSensorValue.setText("Off");
                                binding.humidityValue.setText("Off");
                                reference.child("Temperature").child("Value").setValue("0");
                                reference.child("Humidity").child("Value").setValue("0");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        reference.child("NotificationCount").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.notificationNumber.setText(snapshot.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        reference.child("MoistureSensor").child("State")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            if (snapshot.getValue(Boolean.class)) {
                                reference.child("MoistureSensor").child("Value").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            binding.moistureSensorValue.setText(snapshot.getValue(String.class));
                                        } else {
                                            binding.moistureSensorValue.setText("0");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            } else {
                                binding.moistureSensorValue.setText("Off");
                                reference.child("MoistureSensor").child("Value").setValue("0");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        refreshThread();
    }

    private void refreshThread() {
        final Handler handler = new Handler();
        final Runnable runnable = () -> refreshPage();
        handler.postDelayed(runnable, 1000);
    }

    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
//            super.onBackPressed();
            finishAffinity();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }
}