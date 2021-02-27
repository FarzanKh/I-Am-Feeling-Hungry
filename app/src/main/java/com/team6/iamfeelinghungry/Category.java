package com.team6.iamfeelinghungry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("title")
    @Expose
    private String title;

    /**
     * No args constructor for use in serialization
     *
     */
    public Category() {
    }

    public Category(String title) {
        super();
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
