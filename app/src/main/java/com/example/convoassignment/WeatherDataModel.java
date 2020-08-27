package com.example.convoassignment;

public class WeatherDataModel {
    String date;
    String temperature;
    String description;
    String speed;
    String city;
    String humidity;
    String time;
    int dayIndex;

    public int getDayIndex() {
        return dayIndex;
    }

    public void setDayIndex(int dayIndex) {
        this.dayIndex = dayIndex;
    }

    public WeatherDataModel(String date, String temperature, String description, String speed, String city, String humidity, String time) {
        this.date = date;
        this.temperature = temperature;
        this.description = description;
        this.speed = speed;
        this.city = city;
        this.humidity = humidity;
        this.time = time;
    }
    public WeatherDataModel(String date, String temperature, String description, String speed, String city, String humidity, String time, int dayIndex) {
        this.date = date;
        this.temperature = temperature;
        this.description = description;
        this.speed = speed;
        this.city = city;
        this.humidity = humidity;
        this.time = time;
        this.dayIndex = dayIndex;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
