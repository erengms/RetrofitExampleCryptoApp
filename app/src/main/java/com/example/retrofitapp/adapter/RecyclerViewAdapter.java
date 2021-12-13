package com.example.retrofitapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadrosid.svgloader.SvgLoader;
import com.bumptech.glide.Glide;
import com.example.retrofitapp.R;
import com.example.retrofitapp.databinding.RecyclerRowBinding;
import com.example.retrofitapp.listener.OnUserClickListener;
import com.example.retrofitapp.model.CryptoModel;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CryptoModel> cryptoList;

    private String[] colors = {"#770055", "#2f2f4d"};

    private OnUserClickListener onUserClickListener;

    public RecyclerViewAdapter(ArrayList<CryptoModel> cryptoList, Context context, OnUserClickListener onUserClickListener) {
        this.cryptoList = cryptoList;
        this.context = context;
        this.onUserClickListener = onUserClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // SvgLoader.pluck().with(activity).load(cryptoList.get(position).logo_url, holder.recyclerRowBinding.logoImageview);
        Glide
                .with(context)
                .load(cryptoList.get(position).logo_url)
                .into(holder.recyclerRowBinding.logoImageview);

        holder.recyclerRowBinding.nameTextview.setText(cryptoList.get(position).name);
        holder.recyclerRowBinding.currencyTextview.setText(cryptoList.get(position).currency);
        holder.recyclerRowBinding.priceTextview.setText("$ " + cryptoList.get(position).price);

         if (cryptoList.get(position).isSelected){
             holder.itemView.setBackgroundColor(Color.parseColor(colors[0]));
             holder.recyclerRowBinding.checkImageview.setVisibility(View.VISIBLE);
         } else {
             holder.itemView.setBackgroundColor(Color.parseColor(colors[1]));
             holder.recyclerRowBinding.checkImageview.setVisibility(View.GONE);
         }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserClickListener.onUserClick(holder.getAdapterPosition());
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onUserClickListener.onUserLongClick(holder.getAdapterPosition());
                return false;
            }
        });

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
