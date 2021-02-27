package com.team6.iamfeelinghungry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class YelpDataClass {
    @SerializedName("businesses")
    @Expose
    private List<Business> businesses = null;
    public YelpDataClass() {
    }

    /**
     *
     * @param businesses
     */
    public YelpDataClass(List<Business> businesses) {
        super();
        this.businesses = businesses;
    }

    public List<Business> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<Business> businesses) {
        this.businesses = businesses;
    }

}
