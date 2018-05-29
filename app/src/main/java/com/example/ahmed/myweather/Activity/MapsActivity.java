package com.example.ahmed.myweather.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.example.ahmed.myweather.GetWeather;
import com.example.ahmed.myweather.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static GoogleMap mMap;
    GetWeather getWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        getWeather = new GetWeather(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng egypt = new LatLng(27.68414044087115, 29.87049043178558);
        BitmapDescriptor b = BitmapDescriptorFactory.fromResource(R.mipmap.city_place);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(egypt, 6));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Toast.makeText(MapsActivity.this, latLng.latitude + " , " + latLng.longitude, Toast.LENGTH_SHORT).show();
            }
        });

        getWeather.requestCoordinate();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                GetWeather.cityName = marker.getTitle();
                getWeather.requestWeather();
                startActivity(new Intent(MapsActivity.this, PopupActivity.class));
                return true;
            }
        });
    }
}
