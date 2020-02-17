package fr.iut_amiens.weatherapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.service.notification.Condition;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import fr.iut_amiens.weatherapplication.openweathermap.WeatherManager;
import fr.iut_amiens.weatherapplication.openweathermap.WeatherResponse;

public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private static final int REQUEST_PERMISSION = 1;

    public WeatherTask weathertask = null;
    public TextView temp;
    public TextView weathertext;
    public TextView weatherdesc;
    public TextView pressure;
    public TextView humidity;
    public Location location;
    public TextView speed;
    public TextView update;
    public ImageView weatherimg;

    public TextView weather_title;
    public TextView pressure_title;
    public TextView humidity_title;
    public TextView wind_title;
    public TextView speed_title;
    public TextView update_title;

    public boolean langue = true; //anglais

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temp = findViewById(R.id.Temperature_Value);
        weathertext = findViewById(R.id.Weather_Value);
        weatherdesc = findViewById(R.id.Weather_Value_2);
        humidity = findViewById(R.id.Humidity_Value);
        pressure = findViewById(R.id.Pressure_Value);
        speed = findViewById(R.id.Speed_Value);
        update = findViewById(R.id.LastUpdate_Value);
        weatherimg = findViewById(R.id.WeatherImg);

        weather_title = findViewById(R.id.Weather);
        pressure_title = findViewById(R.id.Pressure);
        humidity_title = findViewById(R.id.Humidity);
        wind_title = findViewById(R.id.Wind);
        speed_title = findViewById(R.id.Speed);
        update_title = findViewById(R.id.LastUpdate);

        Intent i = getIntent();
        langue = i.getBooleanExtra("Lang", true);

        langage(langue);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_PERMISSION);
        } else {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        weathertask = new WeatherTask(this, location);
        weathertask.execute();



        // Récupération de la météo actuelle :

        // WeatherResponse weather = weatherManager.findWeatherByCityName("Amiens");
        // WeatherResponse weather = weatherManager.findWeatherByGeographicCoordinates(49.8942, 2.2957);

        // documentation : https://openweathermap.org/current

        // Récupération des prévisions par nom de la ville :

        // ForecastResponse forecast = weatherManager.findForecastByCityName("Amiens");
        // ForecastResponse forecast = weatherManager.findForecastByGeographicCoordinates(49.8942, 2.2957);

        // documentation : https://openweathermap.org/forecast5
    }

    public void setInfo(WeatherResponse weather) {
        this.setTitle(weather.getName());
        temp.setText(weather.getMain().getTemp() + " °C");
        weathertext.setText(weather.getWeather().get(0).getMain() + "");
        weatherdesc.setText(weather.getWeather().get(0).getDescription() + "");
        pressure.setText(weather.getMain().getPressure() + " hPa");
        humidity.setText(weather.getMain().getHumidity()+ " %");
        speed.setText(weather.getWind().getSpeed() + " m/s");
        update.setText(weather.getDatetime().toString().substring(0,10) + " " + weather.getDatetime().toString().substring(11,19));
        Picasso.with(this).load(weather.getWeather().get(0).getIconUri()).into(weatherimg);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && PackageManager.PERMISSION_GRANTED == grantResults[0]) {

                //Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                displayLocation();
            }

        } else {
            Toast.makeText(this, "Permission Refusée", Toast.LENGTH_SHORT).show();
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @SuppressLint("MissingPermission")
    private void displayLocation() {
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(location == null) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        if (location == null) {


            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            }, null);
        } else {
            Log.d("Activity lol", location.toString());
        }


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.intent) {

            Intent intent = new Intent(this, PrevisionActivity.class);
            intent.putExtra("Location", location);
            intent.putExtra("Langage", langue);
            startActivity(intent);

            return true;
        }

        if (item.getItemId() == R.id.setting) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

            LayoutInflater lif = getLayoutInflater();
            View dialogView = lif.inflate(R.layout.langage_view, null);
            builder.setView(dialogView);
            builder.setTitle("Setting");

            builder.setPositiveButton("Français", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    langue = false;
                    langage(langue);

                }
            });


            builder.setNegativeButton("English", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    langue = true;
                    langage(langue);
                }
            });


            builder.show();

        }
        return super.onOptionsItemSelected(item);
    }

    public void langage(boolean langue) {

        if (langue) {
            weather_title.setText("Weather");
            pressure_title.setText("Pressure");
            wind_title.setText("Wind");
            speed_title.setText("Speed");
            update_title.setText("LastUpdate");
            humidity_title.setText("Humidity");
        } else {
            weather_title.setText("Meteo");
            pressure_title.setText("Pression");
            wind_title.setText("Vent");
            speed_title.setText("Vitesse");
            update_title.setText("Mise à Jour");
            humidity_title.setText("Humidité");
            weathertext.setText(langageMeteo());

        }

    }

    public String langageMeteo () {
        String main_weather = weathertext.getText().toString();
        switch(main_weather) {
            case "Thunderstorm":
                return "Orage";
            case "Drizzle":
                return "Bruine";
            case "Rain":
                return "Pluie";
            case "Snow":
                return "Neige";
            case "Atmosphere":
                return "Atmosphere";
            case "Clear":
                return "Dégager";
            case "Clouds":
                return "Nuageux";
            case "Extreme":
                return "Extreme";
            case "Additional":
                return "Autre";
        }

        return main_weather;

    }
}


