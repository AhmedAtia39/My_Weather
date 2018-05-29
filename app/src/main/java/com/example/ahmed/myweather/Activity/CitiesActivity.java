package com.example.ahmed.myweather.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SearchView;

import com.example.ahmed.myweather.GetWeather;
import com.example.ahmed.myweather.R;
import com.example.ahmed.myweather.WeatherAdapter;

public class CitiesActivity extends AppCompatActivity {

    public static RecyclerView recyclerView;
    WeatherAdapter weatherAdapter;
    GetWeather getWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);

        recyclerView = findViewById(R.id.list_cities);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        getWeather = new GetWeather(this);
        getWeather.requestWeatherItemList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        SearchView search = (SearchView) menu.findItem(R.id.searchFilterCity).getActionView();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ((WeatherAdapter) (recyclerView.getAdapter())).getFilter().filter(query);
                weatherAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ((WeatherAdapter) (recyclerView.getAdapter())).getFilter().filter(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.displayMap:
                startActivity(new Intent(CitiesActivity.this, MapsActivity.class));
                break;
            case R.id.searchAnyCity:
                final EditText edit_city_name = new EditText(CitiesActivity.this);
                final AlertDialog.Builder dialog = new AlertDialog.Builder(CitiesActivity.this);
                dialog.setMessage("Enter City Name");
                dialog.setView(edit_city_name);
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        GetWeather.cityName = edit_city_name.getText().toString();
                        getWeather.requestWeather();
                        startActivity(new Intent(CitiesActivity.this, PopupActivity.class));
                    }
                });
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}