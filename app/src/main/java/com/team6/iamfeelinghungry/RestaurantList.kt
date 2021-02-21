package com.team6.iamfeelinghungry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "RestaurantList"
private const val BASE_URL = "https://api.yelp.com/v3/"
private const val API_KEY = "7mZjgwtvzn-ZNyFATZ9FsmtMNtJCiOSmJKpJSJMallIFpiWUaTrOlejHBU6kcJcFgIxIFEowdivuCDN_flTHVWTOq75lYFEmNhYycOAH4iq4k7zaeVA0GgPmZJoxYHYx"
class RestaurantList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_list)

        val retrofit =
                Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
        val yelpService = retrofit.create(YelpService::class.java)
        yelpService.searchRestaurant("Bearer $API_KEY","Avacado Toast", "New York").enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                Log.i(TAG, "onResponse $response")
                // Todo: Continue from https://youtu.be/F2V-VOHvx0E?t=1373

            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.i(TAG, "onFailure $t")
            }

        })

    }
}