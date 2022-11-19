package umn.ac.id.uas;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListPTAdapter extends RecyclerView.Adapter<ListPTAdapter.ListPTHolder> {
    private LayoutInflater inflater;
    public Context context;
    public ArrayList<PT> mlistpt;
    public ListPTAdapter(Context context, ArrayList<PT>listpt) {
        inflater = LayoutInflater.from(context);
        mlistpt = listpt;
    }

    @NonNull
    @Override
    public ListPTHolder onCreateViewHolder(@NonNull ViewGroup parent, int position){
        View v = inflater.inflate(R.layout.list_pt, parent, false);

        return new ListPTHolder(v, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPTHolder holder, int position) {
        holder.nama.setText(mlistpt.get(position).getNama());
        holder.ptpic.setImageResource(mlistpt.get(position).getPtpic());
        holder.jarak.setText(mlistpt.get(position).getJarak());
        holder.rating.setText(String.valueOf(mlistpt.get(position).getRating()));
        holder.price.setText(String.valueOf(mlistpt.get(position).getPrice()));
        holder.tipe.setText(mlistpt.get(position).getType());
    }


    @Override
    public int getItemCount() {
        return mlistpt.size();
    }

    public class ListPTHolder extends RecyclerView.ViewHolder{
        TextView nama, lokasi, review, jarak, rating, price, tipe;
        ImageView ptpic;
        ConstraintLayout constraintList;
        final ListPTAdapter adap;

        public ListPTHolder(@NonNull View view, ListPTAdapter adap){
            super(view);
            nama = view.findViewById(R.id.namapt);
            rating = view.findViewById(R.id.rating);
            jarak = view.findViewById(R.id.jarak);
            tipe = view.findViewById(R.id.tipept);
            ptpic = view.findViewById(R.id.ptpic);
            price = view.findViewById(R.id.harga);
            constraintList = view.findViewById(R.id.constraintList);
            this.adap = adap;

            constraintList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent MoveToPTDetail = new Intent(view.getContext(), DetailGymActivity.class);
//                    MoveToPTDetail.putExtra("gym", mlistpt.get(getAdapterPosition()));
//                    view.getContext().startActivity(MoveToPTDetail);
                }
            });

        }


    }}
