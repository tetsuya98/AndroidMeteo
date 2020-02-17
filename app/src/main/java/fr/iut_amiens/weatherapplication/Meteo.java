package fr.iut_amiens.weatherapplication;

import android.location.Location;
import android.net.Uri;

/**
 * Created by memorae on 28/03/2018.
 */

public class Meteo {



    public String temp;
    public String weathertext;
    public String weatherdesc;
    public String pressure;
    public String humidity;
    public String speed;
    public String update;
    public Uri weatherimg;

    public boolean lang;

    public Meteo(String p_temp, String p_weather, String p_weatherdesc, String p_pressure, String p_humidity, String p_speed, String p_update, Uri p_weatherimg, boolean p_lang) {
        temp = p_temp;
        weatherdesc = p_weatherdesc;
        weathertext = p_weather;
        pressure = p_pressure;
        humidity = p_humidity;
        speed = p_speed;
        update = p_update;
        weatherimg = p_weatherimg;
        lang = p_lang;
    }

    public String getTemp() {
        return temp;
    }

    public String getWeathertext() {
        return weathertext;
    }

    public String getWeatherdesc() {
        return weatherdesc;
    }

    public String getPressure() {
        return pressure;
    }

    public String getHumidity() {
        return humidity;
    }


    public String getSpeed() {
        return speed;
    }

    public String getUpdate() {
        return update;
    }

    public Uri getWeatherimg() {
        return weatherimg;
    }

    public boolean getLang() {
        return lang;
    }


}
