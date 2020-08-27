package com.example.convoassignment;

import android.provider.BaseColumns;

public class WeatherContract {

    public static final class WeatherTable implements BaseColumns
    {
        public static final String TABLE_NAME="MyWeatherForecast";
        public static final String DATE="date";
        public static final String TEMPERATURE="temperature";
        public static final String DESCRIPTION="description";
        public static final String SPEED="speed";
        public static final String CITY="city";
        public static final String HUMIDITY="humidity";
        public static final String TIME="time";
        public static final String DAYINDEX="dayindex";
    }
}
