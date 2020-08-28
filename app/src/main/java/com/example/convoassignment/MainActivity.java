package com.example.convoassignment;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tl;
    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;


    private NetworkRequestVolley requestObj;

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<WeatherDataModel> data;
    static View.OnClickListener myOnClickListener;
    ImageView weather_iv;

    TextView date, temperature, speed, humidity, description, city;
    String dateString, temperatureString, speedString, humidityString, descriptionString, cityString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //initializing tablayout and viewpager

        tl = (TabLayout) findViewById(R.id.tablayout_id);
        vp = (ViewPager) findViewById(R.id.viewpager_id);
        vpAdapter = new ViewPagerAdapter(getSupportFragmentManager());


        //Add Fragment titles
        vpAdapter.addFragment(new FragmentOne(), "Today");
        vpAdapter.addFragment(new FragmentTwo(), "Day-2");
        vpAdapter.addFragment(new FragmentThree(), "Day-3");
        vpAdapter.addFragment(new FragmentFour(), "Day-4");
        vpAdapter.addFragment(new FragmentFive(), "Day-5");
        vp.setAdapter(vpAdapter);
        tl.setupWithViewPager(vp);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);

        requestObj = NetworkRequestVolley.getInstance(this);
        requestObj.getApiData();



        weather_iv = (ImageView) findViewById(R.id.wtype);

        date = (TextView) findViewById(R.id.date);
        temperature = (TextView) findViewById(R.id.temp);
        speed = (TextView) findViewById(R.id.wind);
        humidity = (TextView) findViewById(R.id.hum);
        description = (TextView) findViewById(R.id.desc);
        city = (TextView) findViewById(R.id.loc);

    }
}
