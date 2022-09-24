package com.myinnovation.smartirrigationsystem.Utitlity;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.text.DecimalFormat;

public class WeatherApi {
    RequestQueue requestQueue;
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "fb37a00bc8754c550d30cb541291f716";
    DecimalFormat df = new DecimalFormat("#.##");

    double temp, feelsLike;
    int humidity;
    float pressure;
    String wind, clouds, city, country, errorVal = "";
    String longitude, latitude, weatherData;
    Context context;

    public WeatherApi(String longitude, String latitude, Context context){
        this.latitude = latitude;
        this.longitude = longitude;
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }


    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void getWeatherDetails() {
        String tempUrl = "";

        if (!latitude.equals("") && !longitude.equals("")) {
            tempUrl = url + "lat=" + latitude + "&" + "lon" + longitude + "&" + "appid" + appid;
            sendRequest(tempUrl);
        } else {
            if (!country.equals("")) {
                tempUrl = url + "?q=" + city + "," + country + "&appid=" + appid;
            } else {
                tempUrl = url + "?q=" + city + "&appid=" + appid;
            }
            sendRequest(tempUrl);
        }

    }

    public String getError(){
        return errorVal;
    }
    public String getWeatherData(){
        return weatherData;
    }

    private void sendRequest(String url){
        StringRequest
                stringRequest
                = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        String output = "";
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
                            weatherData += "Current weather of " + cityName + " (" + countryName + ")"
                                    + "\n Temp: " + df.format(temp) + " °C"
                                    + "\n Feels Like: " + df.format(feelsLike) + " °C"
                                    + "\n Humidity: " + humidity + "%"
                                    + "\n Description: " + description
                                    + "\n Wind Speed: " + wind + "m/s (meters per second)"
                                    + "\n Cloudiness: " + clouds + "%"
                                    + "\n Pressure: " + pressure + " hPa";
//                                tvResult.setText(output);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        errorVal = error.toString();
                    }
                });
        requestQueue.add(stringRequest);
    }
}
