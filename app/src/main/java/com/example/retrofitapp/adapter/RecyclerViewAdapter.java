package com.example.retrofitapp.adapter;

import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitapp.R;
import com.example.retrofitapp.databinding.RecyclerRowBinding;
import com.example.retrofitapp.model.CryptoModel;

import java.net.URL;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<CryptoModel> cryptoList;

    private String[] colors = {"#7b7294", "#770055", "#2f2f4d", "#153c3a"};

    public RecyclerViewAdapter(ArrayList<CryptoModel> cryptoList) {
        this.cryptoList = cryptoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.recyclerRowBinding.nameTextview.setText(cryptoList.get(position).name);
        holder.recyclerRowBinding.currencyTextview.setText(cryptoList.get(position).currency);
        holder.recyclerRowBinding.priceTextview.setText("$ " + cryptoList.get(position).price);
        

        holder.itemView.setBackgroundColor(Color.parseColor(colors[position % 4]));
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
