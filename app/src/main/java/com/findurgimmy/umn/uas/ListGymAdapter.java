package com.findurgimmy.umn.uas;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ListGymAdapter extends RecyclerView.Adapter<ListGymAdapter.ListGymHolder>{
    double dLatitude, dLongitude, a, c, dDistance, meterConversion = 1609, myDistance, earthRadius = 3958.75;
    double latitude, longitude;
    private LayoutInflater inflater;
    public Context context;
    public ArrayList<Gym> mlistgym;
    public ListGymAdapter(Context context, ArrayList<Gym>listgym){
        this.context = context;
        inflater = LayoutInflater.from(context);
        mlistgym = listgym;
    }

    @NonNull
    @Override
    public ListGymHolder onCreateViewHolder(@NonNull ViewGroup parent, int position){
        View v = inflater.inflate(R.layout.list_gym, parent, false);

        SharedPreferences sharedPreferences = context.getSharedPreferences("myKey", MODE_PRIVATE);
        latitude = Double.parseDouble(sharedPreferences.getString("latitude",""));
        longitude = Double.parseDouble(sharedPreferences.getString("longitude",""));

        return new ListGymHolder(v, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ListGymHolder holder, int position){
        holder.nama.setText(mlistgym.get(position).getNama());
        holder.jarak.setText(String.valueOf(getJarak(mlistgym.get(position), latitude, longitude)) + " km away");
        holder.rating.setText(String.valueOf(mlistgym.get(position).getRating()));
        holder.price.setText("Rp " + String.valueOf(mlistgym.get(position).getTeenagePrice() + " - " + mlistgym.get(position).getAdultPrice()));
        holder.tipe.setText(mlistgym.get(position).getTipe());
        Picasso.get().load(mlistgym.get(position).getGambar()).into(holder.gympic);
    }

    @Override
    public int getItemCount() {
        return mlistgym.size();
    }

    public class ListGymHolder extends RecyclerView.ViewHolder{
        TextView nama, jarak, rating, price, tipe;
        ImageView gympic;
        ConstraintLayout constraintList;
        final ListGymAdapter adap;

        public ListGymHolder(@NonNull View view, ListGymAdapter adap){
            super(view);
            nama = view.findViewById(R.id.namagym);
            rating = view.findViewById(R.id.rating);
            jarak = view.findViewById(R.id.jarak);
            tipe = view.findViewById(R.id.tipegym);
            gympic = view.findViewById(R.id.gympic);
            price = view.findViewById(R.id.harga);
            constraintList = view.findViewById(R.id.constraintList);
            this.adap = adap;

            constraintList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent MoveToGymDetail = new Intent(view.getContext(), DetailGymActivity.class);
                    MoveToGymDetail.putExtra("gym", mlistgym.get(getAdapterPosition()));
                    view.getContext().startActivity(MoveToGymDetail);
                }
            });

        }
    }

    private double getJarak(Gym g ,double currLat, double currLong) {
        dLatitude = Math.toRadians(g.getLatitude() - currLat);
        dLongitude = Math.toRadians(g.getLongitude() - currLong);
        a = (Math.sin(dLatitude/2.0) * Math.sin(dLatitude/2.0) +
                Math.sin(dLongitude/2.0) * Math.sin(dLongitude/2.0)) *
                Math.cos(Math.toRadians(currLat)) *
                Math.cos(Math.toRadians(g.getLatitude()));
        c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0-a));
        dDistance = earthRadius * c;
        myDistance = dDistance * meterConversion;
        return Double.parseDouble(new DecimalFormat("#.##").format(myDistance/1000.0));
    }

}
