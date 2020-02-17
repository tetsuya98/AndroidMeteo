package fr.iut_amiens.weatherapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import fr.iut_amiens.weatherapplication.openweathermap.ForecastResponse;

public class PrevisionActivity extends AppCompatActivity {

    private RecyclerView rv;
    public ForecastTask forecasttask = null;
    List<Meteo> previsions = new ArrayList<>();

    public TextView temp;
    public TextView weathertext;
    public TextView weatherdesc;
    public TextView pressure;
    public TextView humidity;
    public TextView speed;
    public TextView update;
    public ImageView weatherimg;

    public TextView weather_title;
    public TextView pressure_title;
    public TextView humidity_title;
    public TextView wind_title;
    public TextView speed_title;
    public TextView update_title;

    public boolean Langue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prevision);

        Intent i = getIntent();

        final Location location = i.getParcelableExtra("Location");
        Langue = i.getBooleanExtra("Langage", true);

        rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new PrevisionAdapter(PrevisionActivity.this, getLayoutInflater()));
        this.setTitle("Prévisions");

        forecasttask = new ForecastTask(this, location);
        forecasttask.execute();






    }


    private void goToMainActivity() {
        Intent i = new Intent(PrevisionActivity.this, MainActivity.class);
        i.putExtra("Lang", Langue);
        startActivity(i);
    }

    public void setInfo(ForecastResponse forecastResponse){

        List<ForecastResponse.Forecast> forecastResponses = forecastResponse.getList();
        int i;

        for(i=0; i<forecastResponses.size(); i++) {

            String p_weather = forecastResponses.get(i).getWeather().get(0).getMain();
            String p_temp = forecastResponses.get(i).getMain().getTemp() + " °C";
            String p_weatherdesc = forecastResponses.get(i).getWeather().get(0).getDescription();
            String p_pressure = forecastResponses.get(i).getMain().getPressure() + " hPa";
            String p_humidity = forecastResponses.get(i).getMain().getHumidity() + " %";
            String p_speed = forecastResponses.get(i).getWind().getSpeed() + " m/s";
            String tmp_update = forecastResponses.get(i).getDatetime().toString();
            String p_update = tmp_update.substring(8,10) + " " + this.setMonth(tmp_update)+ " "+ tmp_update.substring(12,16);



            Uri p_image = forecastResponses.get(i).getWeather().get(0).getIconUri();

            Meteo meteo = new Meteo(p_temp, p_weather, p_weatherdesc, p_pressure, p_humidity, p_speed, p_update, p_image, Langue);

            previsions.add(meteo);
        }


        ((PrevisionAdapter) rv.getAdapter()).add(previsions);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        goToMainActivity();

        return super.onOptionsItemSelected(item);
    }

    public void dialog(Meteo meteo){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater lif = getLayoutInflater();
        View dialogView = lif.inflate(R.layout.dialog_view, null);

        temp = dialogView.findViewById(R.id.dTemperature_Value);
        weathertext = dialogView.findViewById(R.id.dWeather_Value);
        weatherdesc = dialogView.findViewById(R.id.dWeather_Value_2);
        humidity = dialogView.findViewById(R.id.dHumidity_Value);
        pressure = dialogView.findViewById(R.id.dPressure_Value);
        speed = dialogView.findViewById(R.id.dSpeed_Value);
        update = dialogView.findViewById(R.id.dLastUpdate_Value);
        weatherimg = dialogView.findViewById(R.id.dWeatherImg);

        weather_title = dialogView.findViewById(R.id.Weather);
        pressure_title = dialogView.findViewById(R.id.Pressure);
        humidity_title = dialogView.findViewById(R.id.Humidity);
        wind_title = dialogView.findViewById(R.id.Wind);
        speed_title = dialogView.findViewById(R.id.Speed);
        update_title = dialogView.findViewById(R.id.LastUpdate);

        langage(meteo.getLang());

        temp.setText(meteo.getTemp());
        weatherdesc.setText(meteo.getWeatherdesc());
        weathertext.setText(meteo.getWeathertext());
        humidity.setText(meteo.getHumidity());
        pressure.setText(meteo.getPressure());
        speed.setText(meteo.getSpeed());
        update.setText(meteo.getUpdate());
        //weatherimg.setImageURI(meteo.getWeatherimg());
        Picasso.with(this).load(meteo.getWeatherimg()).into(weatherimg);

        builder.setView(dialogView);
        builder.setTitle("Prevision");

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {   Log.d("dialog_ok", "clique"); }
        });

        builder.show();
    }

    public String setMonth(String date) {
        String m_date = date.substring(5,7);
        String s_month = "";
        int int_month = Integer.parseInt(m_date);

        switch (int_month) {
            case 01:
                s_month = "janvier";
                break;
            case 02:
                s_month = "fevrier";
                break;
            case 03:
                s_month = "mars";
                break;
            case 04:
                s_month = "avril";
                break;
            case 05:
                s_month = "mai";
                break;
            case 06:
                s_month = "juin";
                break;
            case 07:
                s_month = "juillet";
                break;
            case 8:
                s_month = "aout";
                break;
            case 9:
                s_month = "septembre";
                break;
            case 10:
                s_month = "octobre";
                break;
            case 11:
                s_month = "novembre";
                break;
            case 12:
                s_month = "decembre";
                break;

        }
        return s_month;
    }

    public void langage(boolean langue) {

        if (langue) {
            weather_title.setText("Weather");
            pressure_title.setText("Pressure");
            humidity_title.setText("Humidity");
            wind_title.setText("Wind");
            speed_title.setText("Speed");
            update_title.setText("LastUpdate");
        } else {
            weather_title.setText("Meteo");
            pressure_title.setText("Pression");
            humidity_title.setText("Humidité");
            wind_title.setText("Vent");
            speed_title.setText("Vitesse");
            update_title.setText("Mise à Jour");
            weathertext.setText(this.langageMeteo(weathertext.getText().toString()));

        }

    }

    public String langageMeteo (String weather) {
        String main_weather = weather;
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
