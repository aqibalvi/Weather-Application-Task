package com.example.convoassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class WeatherAppDBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private SQLiteDatabase dbRead;
    SQLiteOpenHelper dbh = null;

    public static final String DB_NAME = "myWeatherApp.db";
    public static final int DB_VERSION = 1;


    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + WeatherContract.WeatherTable.TABLE_NAME;

    public WeatherAppDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //Creating Weather table

         final String CREATE_WEATHER_TABLE = "CREATE TABLE " +
                WeatherContract.WeatherTable.TABLE_NAME + " (" +
                WeatherContract.WeatherTable.DATE + " TEXT," +
                WeatherContract.WeatherTable.TEMPERATURE + " TEXT," +
                WeatherContract.WeatherTable.DESCRIPTION + " TEXT," +
                WeatherContract.WeatherTable.SPEED + " TEXT," +
                WeatherContract.WeatherTable.CITY + " TEXT," +
                WeatherContract.WeatherTable.HUMIDITY + " TEXT," +
                WeatherContract.WeatherTable.TIME + " TEXT," +
                WeatherContract.WeatherTable.DAYINDEX + " NUMBER);";
                sqLiteDatabase.execSQL(CREATE_WEATHER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean isTableEmpty(Cursor cursor) {
        return !(cursor.getCount() > 0);
    }

    public void removeAllRowsFromWeatherTable(){

        //Reference for write only data only data

        dbRead = this.getWritableDatabase();
        Log.d("insert data", "");

        // get reference to writable DB
        dbRead.delete(WeatherContract.WeatherTable.TABLE_NAME,null,null);
    }


    public List<WeatherDataModel> readAllData()
    {
        //Reference for read only data
        db = this.getReadableDatabase();
        List<WeatherDataModel> weatherList = new ArrayList<>();

        //Selecting the rows for query to read

        String[] projection={
                WeatherContract.WeatherTable.DATE,
                WeatherContract.WeatherTable.TEMPERATURE,
                WeatherContract.WeatherTable.DESCRIPTION,
                WeatherContract.WeatherTable.SPEED,
                WeatherContract.WeatherTable.CITY,
                WeatherContract.WeatherTable.HUMIDITY,
                WeatherContract.WeatherTable.TIME,
                WeatherContract.WeatherTable.DAYINDEX
        };

        //Sorting in ascending order according to index (day) number

        String sort = WeatherContract.WeatherTable.DAYINDEX+ " ASC";
        Cursor cursor=db.query
                (WeatherContract.WeatherTable.TABLE_NAME,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        sort);

        while(cursor.moveToNext()) {


            //"READING FROM DATABASE"

                weatherList.add( new WeatherDataModel(
                cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherTable.DATE)),
                cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherTable.TEMPERATURE)),
                cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherTable.DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherTable.SPEED)),
                cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherTable.CITY)),
                cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherTable.HUMIDITY)),
                cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherTable.TIME)),
                cursor.getInt(cursor.getColumnIndex(WeatherContract.WeatherTable.DAYINDEX))
                ));

        }
        cursor.close();

        return weatherList;
    }


}
