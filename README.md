[![LinkedIn][linkedin-shield]][linkedin-url]

# Weather-Application-Task
A task given by Convo Technologies to build a weather application in Android.

<!-- ABOUT THE PROJECT -->
## About The Project

An evaluation assessment given by Convo Technologies to build a weather forecast application. User can see the weather forecast of coming 5 days including the present day.
Each day has its own screen and contains weather forecast of 3 hours interval for that particular day. Application will ask for the system's location permission if first 
time user installing the app. First time data will be fetched directly through API which will take 1-2 seconds to load the request (Recommended: switch to other fragments meanwhile). The first time fetched data will immediately save in SQLite Databse within the device, this will help in fetching new data next time and not to wait for the API request. The new data will be updated in database and fetched from SQLite every next time.


## Getting Started
Install through debugging mode or make the APK to run the application on your device. 

### Obtaining Data
Data fetched by the openWeatherMap API relevant to the user's system location, requesting the weather information through GET method using Volley library.


### Pre-Requisites
Libraries used in the project are:
* [Volley](https://developer.android.com/training/volley)
* [Fragments](https://developer.android.com/guide/components/fragments)
* [SQLiteOpenHelper](https://developer.android.com/reference/android/database/sqlite/SQLiteOpenHelper)
* [RecyclerView](https://developer.android.com/jetpack/androidx/releases/recyclerview)
* [LocationListener](https://developer.android.com/reference/android/location/LocationListener)
* Android Studio (An effecient tool for android development)

<!-- USAGE EXAMPLES -->
## Usage
User's location will get automatically by the system and relevant data will be fetched through API. User can see the details of each day in 3 hours interval by scrolling 
the screen.
Show you a very accurate weather forecast to plan your schedule accordingly.

## Contact Authors
<!-- CONTACT Author -->

**Aqib Alvi** - aqib259alvi@gmail.com

**Project Link:** [https://github.com/aqibalvi/Weather-Application-Task](https://github.com/aqibalvi/Weather-Application-Task)

[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=flat-square&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/aqib-alvi-465a89ba/
