package com.rxadnroid.rxandroidtest.app.api;

import com.rxadnroid.rxandroidtest.app.entity.Signinlist;
import com.rxadnroid.rxandroidtest.app.entity.TaskList;
import com.rxadnroid.rxandroidtest.app.rxhttp.HttpResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 子非鱼 on 2017/1/19.
 */
public interface SigninlistApi {



    @GET("getMySigninlist")
    Observable<List<Signinlist>> getMySigninlist(@Query("currentuserid") String currentuserid , @Query("limit") int limit , @Query("page") int page);



}
