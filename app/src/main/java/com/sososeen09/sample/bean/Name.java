package com.sososeen09.sample.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yunlong.su on 2017/6/13.
 */

public class Name extends Attribute {


    /**
     * first-name : Su
     * last-name : Tu
     */

    @SerializedName("first-name")
    private String firstname;
    @SerializedName("last-name")
    private String lastname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
