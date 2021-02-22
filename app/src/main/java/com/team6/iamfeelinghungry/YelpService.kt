package com.team6.iamfeelinghungry

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface YelpService {
    @GET("businesses/search")
    fun searchRestaurant(
            @Header("Authorization") authHeader: String,
            @Query("term") searchTerm: String,
            @Query("location") location: String) : Call<YelpSearchResult>
}