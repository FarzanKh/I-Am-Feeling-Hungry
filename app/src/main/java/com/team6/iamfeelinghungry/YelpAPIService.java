package com.team6.iamfeelinghungry;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface YelpAPIService {

    @GET("businesses/search")
    Call<YelpDataClass> getRestaurants(@Header ("Authorization") String authHeader, @Query("term") String searchTerm, @Query("price") String price,
                                       @Query("transactions") String transactions, @Query("location") String location, @Query("limit") int limit);

}