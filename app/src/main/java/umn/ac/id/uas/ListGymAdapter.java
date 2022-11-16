package umn.ac.id.uas;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListGymAdapter extends RecyclerView.Adapter<ListGymAdapter.ListGymHolder>{
    private LayoutInflater inflater;
    public Context context;
    public ArrayList<Gym> mlistgym;
    public ListGymAdapter(Context context, ArrayList<Gym>listgym){
        inflater = LayoutInflater.from(context);
        mlistgym = listgym;
    }

    @NonNull
    @Override
    public ListGymHolder onCreateViewHolder(@NonNull ViewGroup parent, int position){
        View v = inflater.inflate(R.layout.list_gym, parent, false);

        return new ListGymHolder(v, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ListGymHolder holder, int position){
        holder.nama.setText(mlistgym.get(position).getNama());
        holder.gympic.setImageResource(mlistgym.get(position).getGympic());
        holder.jarak.setText(mlistgym.get(position).getJarak());
        holder.rating.setText(String.valueOf(mlistgym.get(position).getRating()));
        holder.teenagePrice.setText(String.valueOf(mlistgym.get(position).getTeenagePrice()));
        holder.tipe.setText(mlistgym.get(position).getTipe());
    }

    @Override
    public int getItemCount() {
        return mlistgym.size();
    }

    public class ListGymHolder extends RecyclerView.ViewHolder{
        TextView nama, lokasi, review, jarak, rating, teenagePrice, adultPrice, tipe;
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
            teenagePrice = view.findViewById(R.id.harga);
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

}
