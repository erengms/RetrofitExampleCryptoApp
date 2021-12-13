package com.example.retrofitapp.model;


import com.google.gson.annotations.SerializedName;

public class CryptoModel {

    @SerializedName("name") //bitcoin
    public String name;

    @SerializedName("currency") //birimi -> BTC
    public String currency;

    @SerializedName("price")
    public String price;

    @SerializedName("logo_url")
    public String logo_url;

    public boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
