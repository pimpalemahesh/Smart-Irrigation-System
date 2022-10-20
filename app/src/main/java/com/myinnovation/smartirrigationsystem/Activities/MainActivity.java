package com.myinnovation.smartirrigationsystem.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.myinnovation.smartirrigationsystem.R;
import com.myinnovation.smartirrigationsystem.Utitlity.WeatherApi;
import com.myinnovation.smartirrigationsystem.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    RequestQueue requestQueue;
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "fb37a00bc8754c550d30cb541291f716";
    DecimalFormat df = new DecimalFormat("#.##");

    private String weatherData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        weatherThread thread = new weatherThread();
        thread.start();
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        binding.soilMoistureCardView.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MoistureSensorActivity.class)));
        binding.temperatureCardView.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TemperatureSensorActivity.class)));

        binding.toMotorActivity.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AllMoistureSensorActivity.class)));
        binding.setting.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SettingActivity.class)));
        binding.weatherCardView.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, WeatherActivity.class).putExtra("WR", weatherData)));
        WeatherApi api = new WeatherApi("Mumbai", "India", getApplicationContext());
    }

    private class weatherThread extends Thread{

        @Override
        public void run() {
            getWeatherDetails();
        }
    }
    public void getWeatherDetails(){
        String tempUrl = "";
        String city = "Sangli";
        String country = "India";
        if(city.equals("")){
        }else{
            if(!country.equals("")){
                tempUrl = url + "?q=" + city + "," + country + "&appid=" + appid;
            }else{
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

                                binding.temperature.setText(df.format(temp));
                                binding.rain.setText(clouds);
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

    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }



}