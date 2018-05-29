package com.example.ahmed.myweather;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.ahmed.myweather.Activity.PopupActivity;

import java.util.ArrayList;

/**
 * Created by AHMED on 25/05/2018.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> implements Filterable {

    ArrayList<WeatherDetails> items;
    ArrayList<WeatherDetails> filterList;
    CustomFilter filter;
    Context context;

    public WeatherAdapter(Context context, ArrayList<WeatherDetails> items) {
        this.items = items;
        this.filterList = items;
        this.context = context;
    }


    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v1 = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
        ViewHolder v = new ViewHolder(v1);
        return v;
    }

    @Override
    public void onBindViewHolder(WeatherAdapter.ViewHolder holder, int position) {
        holder.itemCityName.setText(items.get(position).city_name);
        holder.itemTemp.setText(items.get(position).current_temp);
        holder.itemHumidity.setText(items.get(position).humidity);
        holder.itemWindDegree.setText(items.get(position).wind_degree);
        holder.itemWindSpeed.setText(items.get(position).wind_speed);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter(filterList, this);
        }

        return filter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemCityName, itemTemp, itemHumidity, itemWindDegree, itemWindSpeed;

        public ViewHolder(final View itemView) {
            super(itemView);
            itemCityName = itemView.findViewById(R.id.item_city_name);
            itemTemp = itemView.findViewById(R.id.item_temp);
            itemHumidity = itemView.findViewById(R.id.item_humidity);
            itemWindDegree = itemView.findViewById(R.id.item_wind_degree);
            itemWindSpeed = itemView.findViewById(R.id.item_wind_speed);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GetWeather.cityName = itemCityName.getText().toString();
                    context.startActivity(new Intent(context, PopupActivity.class));

                }
            });
        }
    }
}

