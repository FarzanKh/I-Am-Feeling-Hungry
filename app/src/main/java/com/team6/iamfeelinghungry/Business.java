package com.team6.iamfeelinghungry;

import android.location.Location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Business {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("review_count")
    @Expose
    private int reviewCount;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("rating")
    @Expose
    private double rating;
    @SerializedName("transactions")
    @Expose
    private List<String> transactions = null;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("location")
    @Expose
    private YelpLocation location;
    @SerializedName("distance")
    @Expose
    private double distance;

    /**
     * No args constructor for use in serialization
     *
     */
    public Business() {
    }

    /**
     *
     * @param distance
     * @param rating
     * @param transactions
     * @param reviewCount
     * @param price
     * @param imageUrl
     * @param name
     * @param location
     * @param id
     * @param categories
     */
    public Business(String id, String name, String imageUrl, int reviewCount, List<Category> categories, double rating, List<String> transactions, String price, YelpLocation location, double distance) {
        super();
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.reviewCount = reviewCount;
        this.categories = categories;
        this.rating = rating;
        this.transactions = transactions;
        this.price = price;
        this.location = location;
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<String> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<String> transactions) {
        this.transactions = transactions;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public YelpLocation getLocation() {
        return location;
    }

    public void setLocation(YelpLocation location) {
        this.location = location;
    }


    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    // Convert meters to miles
    public String convertDistance() {
        Double miles = 0.000621371;
        Double distanceInMiles = this.distance * miles;
        String result = String.format("%.2f", distanceInMiles);
        return result + " mi";
    }

}
