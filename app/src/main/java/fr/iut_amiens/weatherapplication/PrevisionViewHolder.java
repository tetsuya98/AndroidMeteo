package fr.iut_amiens.weatherapplication;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import fr.iut_amiens.weatherapplication.openweathermap.ForecastResponse;

import static android.support.v4.content.ContextCompat.startActivity;
import static android.widget.Toast.makeText;

/**
 * Created by memorae on 28/03/2018.
 */

public class PrevisionViewHolder extends RecyclerView.ViewHolder {

    //declaration des champs de la cardview
    private ConstraintLayout cl;
    private TextView weather;
    private ImageView meteoimg;
    private TextView temp;
    private Context context;
    private TextView update;
    private Meteo meteo_dialog;
    private TextView weather_title;


    public PrevisionViewHolder(final Context p_context, View itemView) {
        super(itemView);

        meteoimg = itemView.findViewById(R.id.meteoImg);
        weather = itemView.findViewById(R.id.meteo_Value);
        temp = itemView.findViewById(R.id.tempe_value);
        update = itemView.findViewById(R.id.date_value);
        weather_title = itemView.findViewById(R.id.meteo);
        context = p_context;
        cl = itemView.findViewById(R.id.cl);
        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PrevisionActivity) p_context).dialog(meteo_dialog);

            }
        });
    }

    public void bind(Meteo meteo) {
        //assignement des données meteos dans la cardview
        boolean lang = meteo.getLang();
        if (lang) {
            weather_title.setText("Weather");
            weather.setText(meteo.getWeathertext());
        }else {
            weather_title.setText("Meteo");
            weather.setText(((PrevisionActivity) context).langageMeteo(meteo.getWeathertext()));
        }
        meteo_dialog = meteo;
        //weather.setText(meteo.getWeathertext());
        temp.setText(meteo.getTemp());
        //meteoimg.setImageURI(meteo.getWeatherimg());
        Picasso.with(context).load(meteo.getWeatherimg()).into(meteoimg);
        update.setText(meteo.getUpdate());


    }
}
