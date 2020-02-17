package fr.iut_amiens.weatherapplication;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
//import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;

import fr.iut_amiens.weatherapplication.openweathermap.ForecastResponse;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by memorae on 28/03/2018.
 */


public class PrevisionAdapter extends RecyclerView.Adapter<PrevisionViewHolder> {

    private List<Meteo> meteos = new ArrayList<>();
    private LayoutInflater lif;
    private Context context;
    private View itemview;

    public PrevisionAdapter (Context context, LayoutInflater layoutinflater) {
        this.lif = layoutinflater;
        this.context = context;
    }
    @Override
    public PrevisionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemview = lif.inflate(R.layout.item_meteo, parent, false);


        return new PrevisionViewHolder(context, itemview);
    }

    @Override
    public void onBindViewHolder(PrevisionViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return meteos.size();
    }


    public Meteo getItem(int position) {
        return meteos.get(position);
    }

    public void add(List<Meteo> p_meteos){
        meteos = p_meteos;

        notifyDataSetChanged();
    }

}
