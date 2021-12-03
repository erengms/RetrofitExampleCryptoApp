package com.example.retrofitapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.retrofitapp.R;
import com.example.retrofitapp.adapter.RecyclerViewAdapter;
import com.example.retrofitapp.databinding.ActivityMainBinding;
import com.example.retrofitapp.model.CryptoModel;
import com.example.retrofitapp.service.CryptoApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// https://api.nomics.com/v1/prices?key=742819bd2ae34b03867b06abd443c3bad654442a
// logolu https://api.nomics.com/v1/currencies/ticker?key=742819bd2ae34b03867b06abd443c3bad654442a
// id'ye göre https://api.nomics.com/v1/currencies/ticker?key=742819bd2ae34b03867b06abd443c3bad654442a&ids=BTC,ETH
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private ArrayList<CryptoModel> cryptoModels;
    private Retrofit retrofit;
    private String BASE_URL = "https://api.nomics.com/v1/";

    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Retrofit & JSON
        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        loadData();
    }

    private void loadData(){
        CryptoApi cryptoApi = retrofit.create(CryptoApi.class);

        Call<List<CryptoModel>> call = cryptoApi.getData();
        call.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                if (response.isSuccessful()){
                    cryptoModels = new ArrayList<>(response.body());

                    //RecyclerView
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerViewAdapter = new RecyclerViewAdapter(cryptoModels, MainActivity.this);
                    binding.recyclerView.setAdapter(recyclerViewAdapter);

                    /*for (CryptoModel cryptoModels: cryptoModels) {
                        Log.d("@@@@", cryptoModels.currency);
                    }*/
                }
            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}