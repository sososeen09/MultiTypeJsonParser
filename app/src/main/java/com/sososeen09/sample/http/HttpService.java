package com.sososeen09.sample.http;

import com.sososeen09.sample.bean.ListInfoWithType;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created on 2018/5/10.
 *
 * @author sososeen09
 */

public interface HttpService {

    @GET("user/attrs")
    Observable<ListInfoWithType> getUserAttrs();
}
