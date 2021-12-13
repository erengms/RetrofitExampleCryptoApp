package com.example.retrofitapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

// https://api.nomics.com/v1/prices?key=742819bd2ae34b03867b06abd443c3bad654442a
// logolu https://api.nomics.com/v1/currencies/ticker?key=742819bd2ae34b03867b06abd443c3bad654442a
// id'ye göre https://api.nomics.com/v1/currencies/ticker?key=742819bd2ae34b03867b06abd443c3bad654442a&ids=BTC,ETH
public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnUserClickListener {

    private ActivityMainBinding binding;

    private ArrayList<CryptoModel> cryptoModels;
    private Retrofit retrofit;
    private String BASE_URL = "https://api.nomics.com/v1/";

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private CompositeDisposable compositeDisposable;

    private ActionMode actionMode;
    private CryptoModel selectedCryptoModel;

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
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) //retrofit'e RxJava ile çalışacağımızı söylüyoruz
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        loadData();

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.divider));
        binding.recyclerView.addItemDecoration(dividerItemDecoration);

    }

    private void loadData(){
       final CryptoApi cryptoApi = retrofit.create(CryptoApi.class);

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(cryptoApi.getData()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::handleResponse)); //getData bana bir CryptoModel listesi veriyor, onu handleResponse metodunda direk işleyebiliriz.

        binding.swipeRefreshLayout.setRefreshing(false);

        // retrofit Call ile yapılan işlemleri RxJava'ya çevirecez
       /* Call<List<CryptoModel>> call = cryptoApi.getData();
        call.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                if (response.isSuccessful()){
                    cryptoModels = new ArrayList<>(response.body());

                    //RecyclerView
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerViewAdapter = new RecyclerViewAdapter(cryptoModels, MainActivity.this);
                    binding.recyclerView.setAdapter(recyclerViewAdapter);

                    *//*for (CryptoModel cryptoModels: cryptoModels) {
                        Log.d("@@@@", cryptoModels.currency);
                    }*//*
                }
            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });*/
    }

    private void handleResponse(List<CryptoModel> cryptoModelList){
        cryptoModels = new ArrayList<>(cryptoModelList);

        //RecyclerView
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerViewAdapter = new RecyclerViewAdapter(cryptoModels, MainActivity.this, this);
        binding.recyclerView.setAdapter(recyclerViewAdapter);

    }

    ActionMode.Callback actionModeCallBack = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getMenuInflater().inflate(R.menu.delete_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.delete_item){
                ArrayList<CryptoModel> tempList = new ArrayList<>();
                tempList.addAll(cryptoModels);

                for (CryptoModel crypto: tempList) {
                    if (crypto.isSelected){
                        cryptoModels.remove(crypto);
                    }
                }
                recyclerViewAdapter.notifyDataSetChanged();
                actionMode.finish();
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            for(int i=0; i<cryptoModels.size(); i++){
                cryptoModels.get(i).setSelected(false);
            }
            actionMode = null;
            recyclerViewAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        compositeDisposable.clear();
    }

    @Override
    public void onUserClick(int position) {
        selectedCryptoModel = cryptoModels.get(position);
    }

    @Override
    public void onUserLongClick(int position) {
        if (actionMode == null){
            actionMode = startActionMode(actionModeCallBack);
        }

        //toggle selection
        CryptoModel crypto = cryptoModels.get(position);
        cryptoModels.get(position).setSelected(!crypto.isSelected());

        recyclerViewAdapter.notifyDataSetChanged();

        int total = 0;
        for(CryptoModel cryptoModel : cryptoModels){
            if (cryptoModel.isSelected){
                total++;
            }
        }
        actionMode.setTitle(total + " eleman seçildi");
    }

}