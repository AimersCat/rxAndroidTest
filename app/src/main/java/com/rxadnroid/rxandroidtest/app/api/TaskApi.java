package com.rxadnroid.rxandroidtest.app.api;

import com.rxadnroid.rxandroidtest.app.entity.TaskList;
import com.rxadnroid.rxandroidtest.app.rxhttp.HttpResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 子非鱼 on 2017/1/19.
 */
public interface TaskApi {

    //http://219.143.10.223:6089/api_hongtu/console/getTaskTodoList

//    @GET("http://219.143.10.223:6089/api_hongtu/getTaskTodoList")


    @GET("getTaskTodoList")
    Observable<List<TaskList>> getUserObservable(@Query("currentuserid") String currentuserid);


//    @GET("/microservice/weather")
//    void getResult(@Query("citypinyin") String citypinyin, Callback<Result> cb);

//    @GET("/microservice/weather")
//    Observable<HttpResult> getUserObservable(@Query("citypinyin") String currentuserid) ;


    @GET("http://219.143.10.223:6089/api_hongtu/getServerTime")
    Observable<HttpResult> getServerTime();


}
