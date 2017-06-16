package com.sososeen09.multitypejsonparser.parse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunlong.su on 2017/6/16.
 */

public class ListItemFilter {
    /**
     * 过滤为null的元素
     * @param list
     * @param <V>
     * @return
     */
    @SuppressWarnings("SuspiciousMethodCalls")
    public static <V> List<V> filterNullElement(List<V> list) {
        ArrayList<?> nullGroup = new ArrayList<>();
        nullGroup.add(null);
        list.removeAll(nullGroup);
        return list;
    }
}
