package com.team6.iamfeelinghungry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardActivity extends Activity {
    private Button logout;
    static final String BASE_URL = "https://api.yelp.com/v3/businesses/";
    static Retrofit retrofit = null;

    private FavoriteRestaurantAdapter favoriteRestaurantAdapter;
    private List<FavoriteBusiness> favoriteBusinessList;
    private RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);
        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(DashboardActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        favoriteBusinessList = new ArrayList<>();
        favoriteRestaurantAdapter = new FavoriteRestaurantAdapter(favoriteBusinessList, this);
        connect();
        recyclerView = findViewById(R.id.rvRestaurantList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(favoriteRestaurantAdapter);



    }

    private void connect() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        /*

        Hey Greg, I'm gonna keep this note here just as a reminder for myself for our meeting:
---------
The API end-point https://api.yelp.com/v3/businesses/{id} is not suited for our recycleView in the
Dashboard since this API call returns only one restaurant not a list of restaurants. So even if we



         */

        YelpAPIService yelpAPIService = retrofit.create(YelpAPIService.class);

        Call<YelpDataClass> call = yelpAPIService.getRestaurants("Bearer " + RestaurantList.API_KEY, /* restaurant id */);

        call.enqueue(new Callback<YelpDataClass>() {
            @Override
            public void onResponse(Call<YelpDataClass> call, Response<YelpDataClass> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Log.i(TAG, "onResponse $response");

                    businessList = response.body().getBusinesses();
                    restaurantListAdapter = new RestaurantListAdapter(businessList, RestaurantList.this);
                    recyclerView.setAdapter(restaurantListAdapter);
                }
            }

            @Override
            public void onFailure(Call<YelpDataClass> call, Throwable t) {
                Log.i(TAG, "onFailure $t");

            }
        });

    }







    public void onTakeQuiz(View view) {
        Intent intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
    }


    // Navigate to Settings Activity
    public void navToSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
