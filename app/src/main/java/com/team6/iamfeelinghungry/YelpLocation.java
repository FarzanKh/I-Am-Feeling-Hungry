package com.team6.iamfeelinghungry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class YelpLocation {
    @SerializedName("address1")
    @Expose
    private String address1;

    /**
     * No args constructor for use in serialization
     *
     */
    public YelpLocation() {
    }

    /**
     *
     * @param address1
     */
    public YelpLocation(String address1) {
        super();
        this.address1 = address1;
    }

    public String getAddress1() {
        return address1;
    }

//    public void setAddress1(String address1) {
//        this.address1 = address1;
//    }

}