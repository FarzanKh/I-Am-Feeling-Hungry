package com.team6.iamfeelinghungry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavoriteBusiness {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("image_url")
    @Expose
    private String imageUrl;

    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;

    @SerializedName("location")
    @Expose
    private YelpLocation location;

    public FavoriteBusiness() {

    }

    public FavoriteBusiness(String id, String name, String imageUrl, List<Category> categories, YelpLocation yelpLocation) {
        super();
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.categories = categories;
        this.location = yelpLocation;
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public YelpLocation getLocation() {
        return location;
    }

    public void setLocation(YelpLocation location) {
        this.location = location;
    }


}
