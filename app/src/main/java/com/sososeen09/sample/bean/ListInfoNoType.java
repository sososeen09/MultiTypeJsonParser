package com.sososeen09.sample.bean;

import java.util.List;

/**
 * Created by yunlong.su on 2017/6/15.
 */

public class ListInfoNoType {
    private int total;
    private List<Attribute> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Attribute> getList() {
        return list;
    }

    public void setList(List<Attribute> list) {
        this.list = list;
    }
}
