package com.example.retrofitapp.service;

import com.example.retrofitapp.model.CryptoModel;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

// https://api.nomics.com/v1/prices?key=742819bd2ae34b03867b06abd443c3bad654442a
// logolu https://api.nomics.com/v1/currencies/ticker?key=742819bd2ae34b03867b06abd443c3bad654442a
// id'ye göre https://api.nomics.com/v1/currencies/ticker?key=742819bd2ae34b03867b06abd443c3bad654442a&ids=BTC,ETH

public interface CryptoApi {

    //GET, POST, UPDATE, DELETE
    //Base URL www.website.com

    @GET("currencies/ticker?key=742819bd2ae34b03867b06abd443c3bad654442a&ids=BTC,ETH,ADA,SOL,MANA,SHIB,BNB,USDT,DOGE,XRP,AVAX,LTC,ATLAS")
    Observable<List<CryptoModel>> getData(); //Observable ile gözlemlenebilir bir veri oluşturuyoruz

   // retrofit ile yapılanı RxJava ile yapacaz
   // Call<List<CryptoModel>> getData();
}
