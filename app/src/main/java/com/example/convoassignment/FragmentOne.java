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
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentOne extends Fragment {

    NetworkRequestVolley requestObj;

    String dateString, temperatureString, speedString, humidityString, descriptionString, cityString, timeString;
    String previousDate = "";

    int iter = 0;
    WeatherAppDBHelper dbHelper;
    private SQLiteDatabase db;

    View v;
    private RecyclerView rv;
    private List<WeatherDataModel> weatherList;
    private List<WeatherDataModel> filteredList;
    private static RecyclerView.Adapter rv_adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.firstday_fragment, container, false);
        rv = (RecyclerView) v.findViewById(R.id.one_recycler_view);

        weatherList = dbHelper.readAllData();
        filteredList = new ArrayList<WeatherDataModel>();

        for(int i = 0; i < weatherList.size(); i++)
        {
            if(weatherList.get(i).getDayIndex() == 0)
            {
                filteredList.add(new WeatherDataModel(weatherList.get(i).getDate(),weatherList.get(i).getTemperature(), weatherList.get(i).getDescription(), weatherList.get(i).getSpeed(),weatherList.get(i).getCity(), weatherList.get(i).getHumidity(), weatherList.get(i).getTime(), weatherList.get(i).getDayIndex()));
            }
        }
        rv_adapter = new CustomAdapter((ArrayList<WeatherDataModel>) filteredList);
        rv_adapter.notifyDataSetChanged();
        layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(rv_adapter);

        return v;
    }

    public FragmentOne() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        filteredList = new ArrayList<WeatherDataModel>();

        requestObj = NetworkRequestVolley.getInstance(getContext());
        dbHelper = new WeatherAppDBHelper(getContext());

        weatherList = new ArrayList<WeatherDataModel>();


        //Reading all data from database
        weatherList = dbHelper.readAllData();

        //Filtering the read data to today fragment
        for(int i = 0; i < weatherList.size(); i++)
        {
            Log.e("Printing to check 1", String.valueOf(filteredList.get(i).getDayIndex()));

            if(weatherList.get(i).getDayIndex() == 0)
            {
                Log.e("Index in Fragment 1", String.valueOf(filteredList.get(i).getDayIndex()));
                filteredList.add(new WeatherDataModel(weatherList.get(i).getDate(),weatherList.get(i).getTemperature(), weatherList.get(i).getDescription(), weatherList.get(i).getSpeed(),weatherList.get(i).getCity(), weatherList.get(i).getHumidity(), weatherList.get(i).getTime(), weatherList.get(i).getDayIndex()));
            }
        }
    }
}
