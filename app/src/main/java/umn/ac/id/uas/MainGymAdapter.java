package umn.ac.id.uas;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.PagerAdapter;


import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainGymAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<Gym> listgymMain;
    double dLatitude, dLongitude, a, c, dDistance, meterConversion = 1609, myDistance, earthRadius = 3958.75;

    public MainGymAdapter(Context context, ArrayList<Gym> listgymMain){
        this.context = context;
        this.listgymMain = listgymMain;
    }

    public int getCount(){
        return listgymMain.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position){
        View view = LayoutInflater.from(context).inflate(R.layout.list_gym_main, container, false);

        ImageView image = view.findViewById(R.id.imageView7);
        TextView nama = view.findViewById(R.id.namagymMain);
        TextView jarak = view.findViewById(R.id.jarakMain);

        SharedPreferences sharedPreferences = context.getSharedPreferences("myKey", MODE_PRIVATE);
        double latitude = Double.parseDouble(sharedPreferences.getString("latitude",""));
        double longitude = Double.parseDouble(sharedPreferences.getString("longitude",""));


        Gym gym = listgymMain.get(position);
        Picasso.get().load(gym.getGambar()).into(image);
        nama.setText(gym.getNama());
        jarak.setText(String.valueOf(getJarak(listgymMain.get(position), latitude, longitude)) + " km away");

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MoveToDetailGym = new Intent(view.getContext(), DetailGymActivity.class);
                MoveToDetailGym.putExtra("gym", listgymMain.get(position));
                view.getContext().startActivity(MoveToDetailGym);
            }
        });

        container.addView(view, position);

        return view;
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

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){
        container.removeView((View)object);
    }
}
