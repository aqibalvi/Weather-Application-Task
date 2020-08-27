package com.example.convoassignment;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<WeatherDataModel> weatherDataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView date, temperature, speed, humidity, description, city, time;
        ImageView w_iv;
        LinearLayout bg;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.date = (TextView) itemView.findViewById(R.id.date);
            this.temperature = (TextView) itemView.findViewById(R.id.temp);
            this.description = (TextView) itemView.findViewById(R.id.desc);
            this.speed = (TextView) itemView.findViewById(R.id.wind);
            this.city = (TextView) itemView.findViewById(R.id.loc);
            this.humidity = (TextView) itemView.findViewById(R.id.hum);
            this.time = (TextView) itemView.findViewById(R.id.time);

            this.bg = (LinearLayout) itemView.findViewById(R.id.bgLayout);

            this.w_iv = (ImageView) itemView.findViewById(R.id.wtype);
        }
    }

    public CustomAdapter(ArrayList<WeatherDataModel> data) {
        this.weatherDataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_card, parent, false);

        view.setOnClickListener(MainActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView dt_tv = holder.date;
        TextView temp_tv = holder.temperature;
        TextView desc_tv = holder.description;
        TextView speed_tv = holder.speed;
        TextView city_tv = holder.city;
        TextView hum_tv = holder.humidity;
        TextView time_tv = holder.time;


        //Setting textViews and imageViews according to the fetched data from database

        dt_tv.setText(weatherDataSet.get(listPosition).getDate());
        temp_tv.setText(weatherDataSet.get(listPosition).getTemperature() + "\u2103");
        desc_tv.setText(weatherDataSet.get(listPosition).getDescription());
        speed_tv.setText("Wind: " + weatherDataSet.get(listPosition).getSpeed() + " km/h");
        city_tv.setText(weatherDataSet.get(listPosition).getCity());
        hum_tv.setText("Humidity: " + weatherDataSet.get(listPosition).getHumidity() + "%");
        time_tv.setText(weatherDataSet.get(listPosition).getTime());

        String checkTimeSplit[] = weatherDataSet.get(listPosition).getTime().split("\\s+");
        String checkTimeAMPM = checkTimeSplit[1];

        String checkTimeSplitDigit[] = checkTimeSplit[0].split(":");

        Log.e(" Check DIGIT TIME", checkTimeSplitDigit[0]);
        String checkDigit = checkTimeSplitDigit[0];


        //Show the icon on cardview according to the weather status description

        if(weatherDataSet.get(listPosition).getDescription().equals("Rain"))
        {
//            Log.e("Index in Fragment", String.valueOf(weatherDataSet.get(listPosition).getDayIndex()));

            holder.bg.setBackgroundResource(R.drawable.rainybg);
            holder.w_iv.setImageResource(R.drawable.rain);
        }
        else if(weatherDataSet.get(listPosition).getDescription().equals("Clouds"))
        {
//            Log.e("Index in Fragment", String.valueOf(weatherDataSet.get(listPosition).getDayIndex()));
            holder.bg.setBackgroundResource(R.drawable.cardbg);
            holder.w_iv.setImageResource(R.drawable.cloudy);
        }

        else if(weatherDataSet.get(listPosition).getDescription().equals("Clear"))
        {
            holder.bg.setBackgroundResource(R.drawable.sunnybg);
            holder.w_iv.setImageResource(R.drawable.sun);
        }
    }

    @Override
    public int getItemCount() {
        return weatherDataSet.size();
    }
}
