package fr.iut_amiens.weatherapplication;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.iut_amiens.weatherapplication.openweathermap.WeatherManager;
import fr.iut_amiens.weatherapplication.openweathermap.WeatherResponse;
import fr.iut_amiens.weatherapplication.openweathermap.WeatherResponse.Weather;

/**
 * Created by memorae on 23/03/2018.
 */

public class WeatherTask extends AsyncTask<Object, Object, WeatherResponse> {
    private WeatherManager weatherManager;
    private final Context context;
    private Location location;


    public WeatherTask(Context p_context, Location p_location) {
        context = p_context;
        location = p_location;
        weatherManager = new WeatherManager();
    }


    @Override
    protected WeatherResponse doInBackground(Object[] params) {

        WeatherResponse weather = null;


                try {
                    weather = weatherManager.findWeatherByGeographicCoordinates(location.getLatitude(), location.getLongitude());
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println(-1);
                }


        //publishProgress(weather);
        return weather;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(WeatherResponse weather) {
        ((MainActivity)context).setInfo(weather);
    }

    @Override
    protected void onProgressUpdate(Object... object) {

    }

    @Override
    protected void onCancelled() {
    }



}
