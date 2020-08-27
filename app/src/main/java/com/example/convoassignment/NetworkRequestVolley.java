package com.example.convoassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

import static androidx.constraintlayout.widget.Constraints.TAG;

public class NetworkRequestVolley {

    private WeatherAppDBHelper dbHelper;
    private SQLiteDatabase db;
    GpsTracker gpsTracker;
    String strLat, strLong ="";

    private static Context ctx;

    String dateString, temperatureString, speedString, humidityString, descriptionString, cityString, timeString;
    String previousDate = "";
    int iter = 0;

    private static NetworkRequestVolley single_instance = null;

    private List<WeatherDataModel> weatherList;

    // private constructor restricted to this class itself
    private NetworkRequestVolley(Context context)
    {
        ctx = context;
        //Getting the latitude and longitude from GPS for URL

        gpsTracker = new GpsTracker(context);
        strLat = Double.toString(gpsTracker.getLatitude());
        strLong = Double.toString(gpsTracker.getLongitude());
    }

    // static method to create instance of Singleton NetworkRequestVolley class
    public static NetworkRequestVolley getInstance(Context context)
    {
        if (single_instance == null)
            single_instance = new NetworkRequestVolley(context);

        return single_instance;
    }
    public List<WeatherDataModel> getApiData()
    {

        dbHelper = new WeatherAppDBHelper(ctx.getApplicationContext());
        db = dbHelper.getWritableDatabase();
        weatherList = new ArrayList<WeatherDataModel>();
        iter = 0;

        //Overwriting in DB for no duplication
        dbHelper.removeAllRowsFromWeatherTable();

        String url = "http://api.openweathermap.org/data/2.5/forecast?lat="+strLat+"&lon="+strLong+"&appid=97dfb6f0f6f1f8a5dd11986775599a06&units=metric";

        //GET request to fetch the data from above URL

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    String result = response.toString();
                    JSONObject jsonObj = new JSONObject(result);
                    JSONArray list = jsonObj.getJSONArray("list");

                    JSONObject cityObject = jsonObj.getJSONObject("city");

                    cityString = cityObject.getString("name");
                    String[] splitCity = cityString.split("\\s+");
                    cityString = splitCity[0];

                    // Getting JSON Array node list


                    for (int i = 0; i < list.length(); i++) {

                        // Fetching useful attribute from every list index API request URL

                        JSONObject c = list.getJSONObject(i);
                        JSONObject id = c.getJSONObject("main");
                        temperatureString = String.valueOf(id.getInt("temp"));
                        humidityString = String.valueOf(id.getInt("humidity"));

                        JSONArray array = c.getJSONArray("weather");
                        JSONObject object = array.getJSONObject(0);
                        descriptionString = object.getString("main");


                        id = c.getJSONObject("wind");
                        int hourlyWindSpeed = (int) (id.getInt("speed") * 3.6);
                        speedString = String.valueOf(hourlyWindSpeed);
                        dateString =  c.getString("dt_txt");

                        String[] splitStr = dateString.split("\\s+");
                        String time = splitStr[1];
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        Date dt = sdf.parse(time);

                        SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm aa");
                        String formatedTime = sdfs.format(dt);

                        timeString = formatedTime;


                        String mydate = splitStr[0];


                        //if previous iteration date is not equal to current iteration date next day started

                        if(!previousDate.equals(mydate) && i != 0)
                        {
                            iter++;
                        }
                        previousDate = mydate;



                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date dates = format.parse(mydate);
                        format.applyPattern("EEEE, MMM dd");
                        mydate = format.format(dates);
                        dateString = mydate;


                        saveDataToDb(dateString, temperatureString, descriptionString, speedString, cityString, humidityString, timeString, iter);

                        weatherList.add(new WeatherDataModel(dateString, temperatureString, descriptionString, speedString, cityString, humidityString, timeString,iter));
                    }
                }
                catch (JSONException | ParseException e)
                {   e.printStackTrace();    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Error.Response", "Error in Volley Response");

            }
        });

        //FIFO data structure to process the requests in FIFO style.

        RequestQueue queue = null;

        if (queue == null) {
            // getApplicationContext() is key, it keeps you from leaking the Activity
            queue = Volley.newRequestQueue(ctx.getApplicationContext());
        }

        queue.add(jor);

        return weatherList;

    }

    //Saving weather data into database

    public void saveDataToDb(String date, String temperature, String description, String speed, String city, String humidity, String time, int dayIndex)
    {
        try {

            ContentValues contentValues=new ContentValues();
            contentValues.put(WeatherContract.WeatherTable.DATE,date);
            contentValues.put(WeatherContract.WeatherTable.TEMPERATURE,temperature);
            contentValues.put(WeatherContract.WeatherTable.DESCRIPTION,description);
            contentValues.put(WeatherContract.WeatherTable.SPEED,speed);
            contentValues.put(WeatherContract.WeatherTable.CITY,city);
            contentValues.put(WeatherContract.WeatherTable.HUMIDITY,humidity);
            contentValues.put(WeatherContract.WeatherTable.TIME,time);
            contentValues.put(WeatherContract.WeatherTable.DAYINDEX,dayIndex);

            Log.e("SaveDataToDb", "Data inserted in database successfully");

            //Insert operation

            db.insert(WeatherContract.WeatherTable.TABLE_NAME,null,contentValues);//null hack means if column has no value it will put null
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add Weather to database");
        }
    }

}
