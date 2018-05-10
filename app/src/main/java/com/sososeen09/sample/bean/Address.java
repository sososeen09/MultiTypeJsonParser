package com.sososeen09.sample.bean;

/**
 * Created by yunlong.su on 2017/6/13.
 */

public class Address extends Attribute {

    /**
     * street : NanJing Road
     * city : ShangHai
     * country : China
     */

    private String street;
    private String city;
    private String country;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
