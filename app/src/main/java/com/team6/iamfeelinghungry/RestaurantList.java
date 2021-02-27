package com.team6.iamfeelinghungry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestaurantList extends AppCompatActivity {
    static final String TAG = "TAAAG";
    static final String BASE_URL = "https://api.yelp.com/v3/";
    static Retrofit retrofit = null;
    final static String API_KEY = "7mZjgwtvzn-ZNyFATZ9FsmtMNtJCiOSmJKpJSJMallIFpiWUaTrOlejHBU6kcJcFgIxIFEowdivuCDN_flTHVWTOq75lYFEmNhYycOAH4iq4k7zaeVA0GgPmZJoxYHYx";
//    Intent intent = getIntent();

//    static String RESTAURANT_MESSAGE = "restaurantTypeSpinner";
//    static String PRICE_MESSAGE = "priceRangeSpinner";
//    static String LOCATION_MESSAGE = "userLocation";
//    static String TRANSACTION_MESSAGE = "transactionRadio";


//    String searchTerm =intent.getStringExtra("restaurantTypeSpinner");
//    String price = intent.getStringExtra("priceRangeSpinner");
//    String transaction = intent.getStringExtra("transactionType");
//    String location = intent.getStringExtra("userLocation");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        connect();
    }

    private void connect() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        YelpAPIService yelpAPIService = retrofit.create(YelpAPIService.class);

        Call<YelpDataClass> call = yelpAPIService.getRestaurants("Bearer " + API_KEY, "burger", "1", "delivery", "nyc");

        call.enqueue(new Callback<YelpDataClass>() {
            @Override
            public void onResponse(Call<YelpDataClass> call, Response<YelpDataClass> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Log.i(TAG, "onResponse $response");
                }

            }

            @Override
            public void onFailure(Call<YelpDataClass> call, Throwable t) {
                Log.i(TAG, "onFailure $t");

            }
        });

    }
}