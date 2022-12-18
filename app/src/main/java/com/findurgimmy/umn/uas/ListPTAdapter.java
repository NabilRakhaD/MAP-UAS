package com.findurgimmy.umn.uas;

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

import com.squareup.picasso.Picasso;

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
        holder.umur.setText(mlistpt.get(position).getUmur() + " tahun");
        holder.rating.setText(String.valueOf(mlistpt.get(position).getRating()));
        holder.price.setText("Rp " + String.valueOf(mlistpt.get(position).getPrice()));
        holder.tipe.setText(mlistpt.get(position).getKeahlian().get(0) + ", " + mlistpt.get(position).getKeahlian().get(1));
        Picasso.get().load(mlistpt.get(position).getPtpic()).into(holder.ptpic);
    }


    @Override
    public int getItemCount() {
        return mlistpt.size();
    }

    public class ListPTHolder extends RecyclerView.ViewHolder{
        TextView nama, rating, price, tipe, umur;
        ImageView ptpic;
        ConstraintLayout constraintList;
        final ListPTAdapter adap;

        public ListPTHolder(@NonNull View view, ListPTAdapter adap){
            super(view);
            nama = view.findViewById(R.id.namapt);
            rating = view.findViewById(R.id.rating);
            tipe = view.findViewById(R.id.tipept);
            ptpic = view.findViewById(R.id.ptpic);
            price = view.findViewById(R.id.harga);
            umur = view.findViewById(R.id.umur);
            constraintList = view.findViewById(R.id.constraintList);
            this.adap = adap;

            constraintList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent MoveToPTDetail = new Intent(view.getContext(), DetailPTActivity.class);
                    MoveToPTDetail.putExtra("pt", mlistpt.get(getAdapterPosition()));
                    view.getContext().startActivity(MoveToPTDetail);
                }
            });

        }


    }}
