package umn.ac.id.uas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListGymAdapter extends RecyclerView.Adapter<ListGymAdapter.ListGymHolder>{
    private LayoutInflater inflater;
    public Context context;
    public ArrayList<Gym> listgym;
    public ListGymAdapter(Context context, ArrayList<Gym>listvid){
        inflater = LayoutInflater.from(context);
        listgym = listgym;
    }

    @NonNull
    @Override
    public ListGymHolder onCreateViewHolder(@NonNull ViewGroup parent, int position){
        View v = inflater.inflate(R.layout.list_gym, parent, false);

        return new ListGymHolder(v, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ListGymHolder holder, int position){
        
    }

    public class ListGymHolder extends RecyclerView.ViewHolder{
        TextView nama, lokasi, review, jarak, gympic, rating, teenagePrice, adultPrice;
        final GymAdapter adap;

        public ListGymHolder(@NonNull View view, GymAdapter adap){
            super(view);
            nama = view.findViewById(R.id.namagym);
            rating = view.findViewById(R.id.rating);
            jarak = view.findViewById(R.id.jarak);
            gympic = view.findViewById(R.id.gympic);
            teenagePrice = view.findViewById(R.id.harga);
            adultPrice = view.findViewById(R.id.harga);


        }
    }

}
