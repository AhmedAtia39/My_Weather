package com.example.ahmed.myweather;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ahmed.myweather.Activity.CitiesActivity;
import com.example.ahmed.myweather.Activity.MapsActivity;
import com.example.ahmed.myweather.Activity.PopupActivity;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by AHMED on 24/05/2018.
 */

public class GetWeather {
    public static String cityName;
    Context context;
    String url = null;
    String deg;
    BitmapDescriptor bitmapDescriptor;
    ArrayList<WeatherDetails> weatherDetails;
    WeatherAdapter weatherAdapter;
    RequestQueue requestQueue;
    String cities_names[];

    public GetWeather(Context context) {
        this.context = context;
    }

    public StringRequest requestWeather() {
        url = Constants.baseUrl + cityName;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = new JSONArray(jsonObject.getString("weather"));
                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                    String main = jsonObjectWeather.getString("main");
                    String description = jsonObjectWeather.getString("description");

                    JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                    DecimalFormat decimalFormat = new DecimalFormat("00.0");

                    String tem = decimalFormat.format(Float.parseFloat(jsonObjectMain.getString("temp")) - 273.15f);
                    String pressure = jsonObjectMain.getString("pressure");
                    String humidity = jsonObjectMain.getString("humidity");
                    String temp_max = decimalFormat.format(Float.parseFloat(jsonObjectMain.getString("temp_max")) - 273.15f);
                    String temp_min = decimalFormat.format(Float.parseFloat(jsonObjectMain.getString("temp_min")) - 273.15f);

                    JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                    String speed = jsonObjectWind.getString("speed");
                    try {
                        deg = jsonObjectWind.getString("deg");
                    } catch (Exception e) {
                        deg = "null";
                    }

                    PopupActivity.cityName.setText(cityName);
                    PopupActivity.weather.setText(main + " ,");
                    PopupActivity.weatherDescription.setText(description);
                    PopupActivity.currentTemp.setText(tem + " °C");
                    PopupActivity.minTemp.setText(temp_min + " °C");
                    PopupActivity.maxTemp.setText(temp_max + " °C");
                    PopupActivity.humidity.setText(humidity + " %");
                    PopupActivity.pressure.setText(pressure + " hPa");
                    PopupActivity.windDegree.setText(deg);
                    PopupActivity.windSpeed.setText(speed + " m/s");
                } catch (Exception e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PopupActivity.cityName.setText("Error Name");
            }
        });

        return stringRequest;
    }

    public void requestCoordinate() {
        cityName = "";
        String cities_name[];
        cities_name = context.getResources().getStringArray(R.array.City_name);
        bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.city_place);

        for (int i = 0; i < cities_name.length; i++) {
            cityName = cities_name[i];
            url = Constants.baseUrl + cityName;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject jsonObjectCoor = jsonObject.getJSONObject("coord");
                        double lat = jsonObjectCoor.getDouble("lat");
                        double lon = jsonObjectCoor.getDouble("lon");

                        String city_name = jsonObject.getString("name");

                        JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                        DecimalFormat decimalFormat = new DecimalFormat("00.0");
                        String tem = decimalFormat.format(Float.parseFloat(jsonObjectMain.getString("temp")) - 273.15f);

                        MapsActivity.mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title(city_name).icon(bitmapDescriptor));


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });

            Volley.newRequestQueue(context).add(stringRequest);
        }
    }

    //////////////////////////

    public void requestWeatherItemList() {
        weatherDetails = new ArrayList<>();

        cities_names = context.getResources().getStringArray(R.array.City_name);

        for (int i = 0; i < cities_names.length; i++) {
            cityName = cities_names[i];

            url = Constants.baseUrl + cityName;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        String city_name = jsonObject.getString("name");

                        JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                        DecimalFormat decimalFormat = new DecimalFormat("00.0");

                        String tem = decimalFormat.format(Float.parseFloat(jsonObjectMain.getString("temp")) - 273.15f);
                        String humidity = jsonObjectMain.getString("humidity");

                        JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                        String speed = jsonObjectWind.getString("speed");
                        try {
                            deg = jsonObjectWind.getString("deg");
                        } catch (Exception e) {
                            deg = "null";
                        }

                        weatherDetails.add(new WeatherDetails(city_name, tem + " °C", humidity + " %", deg, speed + " m/s"));
                        weatherAdapter = new WeatherAdapter(context, weatherDetails);
                        CitiesActivity.recyclerView.setAdapter(weatherAdapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);

        }
    }
}
