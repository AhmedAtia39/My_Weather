package com.example.ahmed.myweather;

/**
 * Created by AHMED on 25/05/2018.
 */

public class WeatherDetails {

    public String city_name;
    public String current_temp;
    public String humidity;
    public String wind_degree;
    public String wind_speed;

    public WeatherDetails() {
    }

    public WeatherDetails(String city_name, String current_temp, String humidity, String wind_degree, String wind_speed) {
        this.city_name = city_name;
        this.current_temp = current_temp;
        this.humidity = humidity;
        this.wind_degree = wind_degree;
        this.wind_speed = wind_speed;
    }

}
