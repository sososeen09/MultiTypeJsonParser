package com.sososeen09.sample.bean.generic;

/**
 * Created by yunlong.su on 2017/6/22.
 */

public class BaseUpperBean<T> {
    private String type;
    private T attributes;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getAttributes() {
        return attributes;
    }

    public void setAttributes(T attributes) {
        this.attributes = attributes;
    }
}
