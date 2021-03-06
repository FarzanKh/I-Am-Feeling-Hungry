package com.team6.iamfeelinghungry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestaurantListActivity extends AppCompatActivity {
    static final String TAG = "TAAAG";
    static final String BASE_URL = "https://api.yelp.com/v3/";
    static Retrofit retrofit = null;
    final static String API_KEY = "7mZjgwtvzn-ZNyFATZ9FsmtMNtJCiOSmJKpJSJMallIFpiWUaTrOlejHBU6kcJcFgIxIFEowdivuCDN_flTHVWTOq75lYFEmNhYycOAH4iq4k7zaeVA0GgPmZJoxYHYx";


    static String RESTAURANT_MESSAGE = "restaurantTypeSpinner";
    static String PRICE_MESSAGE = "priceRangeSpinner";
    static String TRANSACTION_MESSAGE = "transactionTypeSpinner";
    static String LOCATION_MESSAGE = "userLocation";


    String restaurantType;
    String priceRange;
    String priceRangeFormat;
    String transactionType;
    String location;

    private RestaurantListAdapter restaurantListAdapter;
    private List<Business> businessList;
    private RecyclerView recyclerView;

    ClickListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // remove bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_restaurant_list);

        Intent intent = getIntent();

        restaurantType = intent.getStringExtra(RESTAURANT_MESSAGE).toLowerCase();

        priceRange = intent.getStringExtra(PRICE_MESSAGE);
        if (priceRange.equalsIgnoreCase("$$$$")) {
            priceRangeFormat = "4";
        } else if (priceRange.equalsIgnoreCase("$$$")) {
            priceRangeFormat = "3";
        } else if (priceRange.equalsIgnoreCase("$$")) {
            priceRangeFormat = "2";
        } else if (priceRange.equalsIgnoreCase("$")) {
            priceRangeFormat = "1";
        }

        transactionType = intent.getStringExtra(TRANSACTION_MESSAGE).toLowerCase();
        location = intent.getStringExtra(LOCATION_MESSAGE).toLowerCase();


        businessList = new ArrayList<>();
        restaurantListAdapter = new RestaurantListAdapter(businessList, this, new ClickListener() {
            @Override public void onPositionClicked(int position) {
                // callback performed on click
            }
            @Override public void onFavoriteClicked(int position) {

            }
        });
        connect();
        recyclerView = findViewById(R.id.rvRestaurantList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(restaurantListAdapter);
    }

    private void connect() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        YelpAPIService yelpAPIService = retrofit.create(YelpAPIService.class);

        Call<YelpDataClass> call = yelpAPIService.getRestaurants("Bearer " + API_KEY, restaurantType, priceRangeFormat, transactionType, location, 3);

        call.enqueue(new Callback<YelpDataClass>() {
            @Override
            public void onResponse(Call<YelpDataClass> call, Response<YelpDataClass> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Log.i(TAG, "onResponse $response");

                    businessList = response.body().getBusinesses();
                    restaurantListAdapter = new RestaurantListAdapter(businessList, RestaurantListActivity.this, new ClickListener() {
                        @Override public void onPositionClicked(int position) {
                            // callback performed on click  business.getName()  business.getLocation().getAddress1()
                            Business business = businessList.get(position);
                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi Bro, I just found an amazing restaurant! Check this: " + business.getName() + " / " + business.getLocation().getAddress1()); // + RestaurantListAdapter.businessList
                            sendIntent.setType("text/plain");

                            Intent shareIntent = Intent.createChooser(sendIntent, null);
                            startActivity(shareIntent);
                        }
                        @Override public void onFavoriteClicked(int position) {

                        }
                    });
                    recyclerView.setAdapter(restaurantListAdapter);
                }
            }

            @Override
            public void onFailure(Call<YelpDataClass> call, Throwable t) {
                Log.i(TAG, "onFailure $t");

            }
        });

    }
}