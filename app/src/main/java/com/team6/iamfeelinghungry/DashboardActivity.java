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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardActivity extends Activity {
    private FavoriteRestaurantAdapter favoriteRestaurantAdapter;
    private RecyclerView recyclerView;
    private List<Restaurant> restaurantList = new ArrayList<>();
    private Button logout;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);
        firebaseAuth=FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance("https://hungryapp-d791e-default-rtdb.firebaseio.com/").getReference();
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

        favoriteRestaurantAdapter = new FavoriteRestaurantAdapter(restaurantList, this);
        getListOfRestaurants();
        recyclerView = findViewById(R.id.rvFavoriteRestaurant);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(favoriteRestaurantAdapter);
    }

    public void getListOfRestaurants(){
        Query restaurantsQuery = mDatabase.child("users").child(userId).child("restaurants").orderByChild("timestamp");
        restaurantsQuery.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                HashMap<String,Object> restaurants = (HashMap<String,Object>) task.getResult().getValue();
                if (restaurants != null) {
                    for (Map.Entry<String, Object> restaurantEntry : restaurants.entrySet()) {
                        restaurantList.add(Restaurant.toRestaurant((HashMap<String, String>) restaurantEntry.getValue()));
                    }
                    favoriteRestaurantAdapter = new FavoriteRestaurantAdapter(restaurantList, this);
                    recyclerView.setAdapter(favoriteRestaurantAdapter);
                }
            }
        });
    }

    // to delete later
    public void addRestaurant(View view){
        writeNewRestaurant("100","McDonalds", "Fast Food", "123 Main Street");
        Query restaurantsQuery = mDatabase.child("users").child(userId).child("restaurants").orderByChild("timestamp");
        restaurantsQuery.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                HashMap<String,Object> restaurants = (HashMap<String,Object>) task.getResult().getValue();
                for (Map.Entry<String,Object> restaurantEntry: restaurants.entrySet()){
                    restaurantList = new ArrayList<>();
                    restaurantList.add(Restaurant.toRestaurant((HashMap<String,String>) restaurantEntry.getValue()));
                }
                favoriteRestaurantAdapter = new FavoriteRestaurantAdapter(restaurantList, this);
                recyclerView.setAdapter(favoriteRestaurantAdapter);
            }
        });
    }

    public void writeNewRestaurant(String yelpId, String name, String category, String address){
        // get new restaurant entry key
        String key = mDatabase.child("users").child(userId).child("restaurants").push().getKey();

        // get current timestamp
        Long tsLong = System.currentTimeMillis();
        String timestamp = tsLong.toString();

        // add new restaurant
        Restaurant restaurant = new Restaurant(yelpId,timestamp,name,category,address);
        Map<String,String> restaurantValues = restaurant.toMap();
        Map<String,Object> childUpdates= new HashMap<>();
        childUpdates.put("/users/" + userId + "/restaurants/" + key,restaurantValues);
        mDatabase.updateChildren(childUpdates);
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
