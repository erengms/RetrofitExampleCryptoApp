package com.example.retrofitapp.service;

import com.example.retrofitapp.model.CryptoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

// https://api.nomics.com/v1/prices?key=742819bd2ae34b03867b06abd443c3bad654442a
// logolu https://api.nomics.com/v1/currencies/ticker?key=742819bd2ae34b03867b06abd443c3bad654442a
// id'ye göre https://api.nomics.com/v1/currencies/ticker?key=742819bd2ae34b03867b06abd443c3bad654442a&ids=BTC,ETH

public interface CryptoApi {

    //GET, POST, UPDATE, DELETE
    //Base URL www.website.com

    @GET("currencies/ticker?key=742819bd2ae34b03867b06abd443c3bad654442a")
    Call<List<CryptoModel>> getData();
}
