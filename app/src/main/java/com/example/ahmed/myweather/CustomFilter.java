package com.example.ahmed.myweather;

/**
 * Created by AHMED on 27/05/2018.
 */

import android.widget.Filter;

import java.util.ArrayList;

public class CustomFilter extends Filter {

    WeatherAdapter adapter;
    ArrayList<WeatherDetails> filterList;

    public CustomFilter(ArrayList<WeatherDetails> filterList, WeatherAdapter adapter) {
        this.adapter = adapter;
        this.filterList = filterList;

    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if (constraint != null && constraint.length() > 0) {
            //CHANGE TO UPPER
            constraint = constraint.toString().toUpperCase();
            //STORE OUR FILTERED cities
            ArrayList<WeatherDetails> filteredPlayers = new ArrayList<>();

            for (int i = 0; i < filterList.size(); i++) {
                //CHECK
                if (filterList.get(i).city_name.toUpperCase().contains(constraint)) {
                    //ADD city TO FILTERED cities
                    filteredPlayers.add(filterList.get(i));
                }
            }

            results.count = filteredPlayers.size();
            results.values = filteredPlayers;
        } else {
            results.count = filterList.size();
            results.values = filterList;

        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.items = (ArrayList<WeatherDetails>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();
    }
}