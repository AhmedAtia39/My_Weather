package com.example.ahmed.myweather.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.ahmed.myweather.GetWeather;
import com.example.ahmed.myweather.R;

public class PopupActivity extends AppCompatActivity {

    public static TextView cityName, weather, weatherDescription, currentTemp, minTemp, maxTemp, humidity, pressure, windDegree, windSpeed;
    GetWeather getWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .60));

        cityName = findViewById(R.id.txt_city_name);
        weather = findViewById(R.id.txt_weather);
        weatherDescription = findViewById(R.id.txt_weather_descrption);
        currentTemp = findViewById(R.id.txt_temp);
        minTemp = findViewById(R.id.txt_Min_temp);
        maxTemp = findViewById(R.id.txt_Max_temp);
        humidity = findViewById(R.id.txt_humidity);
        pressure = findViewById(R.id.txt_pressure);
        windDegree = findViewById(R.id.txt_wind_degree);
        windSpeed = findViewById(R.id.txt_wind_speed);

        getWeather = new GetWeather(this);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(getWeather.requestWeather());
    }
}
