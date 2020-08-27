package com.example.convoassignment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentFour extends Fragment {

    String dateString, temperatureString, speedString, humidityString, descriptionString, cityString, timeString;
    String previousDate = "";
    int iter = 0;

    View v;

    WeatherAppDBHelper dbHelper;
    private SQLiteDatabase db;
    NetworkRequestVolley requestObj;

    private RecyclerView rv;
    private List<WeatherDataModel> weatherList;
    private List<WeatherDataModel> filteredList;
    private static RecyclerView.Adapter rv_adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestObj = NetworkRequestVolley.getInstance(getContext());
        dbHelper = new WeatherAppDBHelper(getContext());
        db = dbHelper.getWritableDatabase();
        weatherList = new ArrayList<WeatherDataModel>();
        filteredList = new ArrayList<WeatherDataModel>();

        //Reading all data from database

        weatherList = dbHelper.readAllData();

        //Filtering the read data to day-4 fragment

        for(int i = 0; i < weatherList.size(); i++)
        {
            if(weatherList.get(i).getDayIndex() == 3)
            {
                filteredList.add(new WeatherDataModel(weatherList.get(i).getDate(),weatherList.get(i).getTemperature(), weatherList.get(i).getDescription(), weatherList.get(i).getSpeed(),weatherList.get(i).getCity(), weatherList.get(i).getHumidity(), weatherList.get(i).getTime(), weatherList.get(i).getDayIndex()));
            }
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fourthday_fragment, container, false);
        rv = (RecyclerView) v.findViewById(R.id.four_recycler_view);

        weatherList = dbHelper.readAllData();
        filteredList = new ArrayList<WeatherDataModel>();
        for(int i = 0; i < weatherList.size(); i++)
        {
            if(weatherList.get(i).getDayIndex() == 3)
            {
                filteredList.add(new WeatherDataModel(weatherList.get(i).getDate(),weatherList.get(i).getTemperature(), weatherList.get(i).getDescription(), weatherList.get(i).getSpeed(),weatherList.get(i).getCity(), weatherList.get(i).getHumidity(), weatherList.get(i).getTime(), weatherList.get(i).getDayIndex()));
            }
        }

        rv_adapter = new CustomAdapter((ArrayList<WeatherDataModel>) filteredList);
        layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(rv_adapter);
        return v;
    }

    public FragmentFour() {
    }
}
