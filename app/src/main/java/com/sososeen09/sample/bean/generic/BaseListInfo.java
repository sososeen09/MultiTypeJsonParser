package com.sososeen09.sample.bean.generic;

import java.util.List;

/**
 * Created by yunlong.su on 2017/6/22.
 */

public class BaseListInfo<T> {

    private int total;
    private List<T> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
