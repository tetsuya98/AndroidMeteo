package fr.iut_amiens.weatherapplication;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import fr.iut_amiens.weatherapplication.openweathermap.ForecastResponse;
import fr.iut_amiens.weatherapplication.openweathermap.WeatherManager;
import fr.iut_amiens.weatherapplication.openweathermap.WeatherResponse;

/**
 * Created by memorae on 28/03/2018.
 */

public class ForecastTask extends AsyncTask<Object, Object, ForecastResponse>  {
    private WeatherManager weatherManager;
    private final Context context;
    private List<WeatherResponse.Weather> ListWeather = new ArrayList();
    private Location location;


    public ForecastTask(Context p_context, Location p_location) {
        context = p_context;
        location = p_location;
        weatherManager = new WeatherManager();
    }


    @Override
    protected ForecastResponse doInBackground(Object[] params) {

        ForecastResponse forecast = null;


        try {
            forecast = weatherManager.findForecastByGeographicCoordinates(location.getLatitude(), location.getLongitude());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(-1);
        }
        return forecast;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(ForecastResponse forecast) {
        ((PrevisionActivity)context).setInfo(forecast);
    }

    @Override
    protected void onProgressUpdate(Object... object) {

    }

    @Override
    protected void onCancelled() {
    }


}
