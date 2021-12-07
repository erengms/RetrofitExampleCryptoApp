package com.example.retrofitapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadrosid.svgloader.SvgLoader;
import com.bumptech.glide.Glide;
import com.example.retrofitapp.databinding.RecyclerRowBinding;
import com.example.retrofitapp.model.CryptoModel;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CryptoModel> cryptoList;

    private String[] colors = {"#7b7294", "#770055", "#2f2f4d", "#153c3a", "#bf4f51", "#00a86b", "#9d2933", "#6b8093", "#c6b164", "#640082"};

    public RecyclerViewAdapter(ArrayList<CryptoModel> cryptoList, Context context) {
        this.cryptoList = cryptoList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setBackgroundColor(Color.parseColor(colors[position % 10]));

        holder.recyclerRowBinding.nameTextview.setText(cryptoList.get(position).name);
        holder.recyclerRowBinding.currencyTextview.setText(cryptoList.get(position).currency);
        holder.recyclerRowBinding.priceTextview.setText("$ " + cryptoList.get(position).price);

       // SvgLoader.pluck().with(activity).load(cryptoList.get(position).logo_url, holder.recyclerRowBinding.logoImageview);

         Glide
                    .with(context)
                    .load(cryptoList.get(position).logo_url)
                    .into(holder.recyclerRowBinding.logoImageview);

    }

    @Override
    public int getItemCount() {
        return cryptoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerRowBinding recyclerRowBinding;

        public ViewHolder(RecyclerRowBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding = recyclerRowBinding;
        }
    }
}
