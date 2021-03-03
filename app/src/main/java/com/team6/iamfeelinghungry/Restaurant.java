package com.team6.iamfeelinghungry;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Restaurant {
    public String yelpId;
    public String timestamp;
    public String name;
    public String category;
    public String address;

    public Restaurant() {
        // Default constructor required for calls to DataSnapshot.getValue(Restaurant.class)
    }

    public Restaurant(String yelpId, String timestamp, String name, String category, String address) {
        this.yelpId = yelpId;
        this.timestamp = timestamp;
        this.name = name;
        this.category = category;
        this.address = address;
    }

    @Exclude
    public Map<String,String> toMap(){
        HashMap<String,String> result = new HashMap<>();
        result.put("yelpId", yelpId);
        result.put("timestamp", timestamp);
        result.put("name", name);
        result.put("category", category);
        result.put("address", address);

        return result;
    }

    @Exclude
    public static Restaurant toRestaurant(HashMap<String, String> restaurantMap){
        String yelpId_map = restaurantMap.get("yelpId");
        String timestamp_map = restaurantMap.get("timestamp");
        String name_map = restaurantMap.get("name");
        String category_map = restaurantMap.get("category");
        String address_map = restaurantMap.get("address");

        return new Restaurant(yelpId_map,timestamp_map,name_map,category_map,address_map);
    }
}