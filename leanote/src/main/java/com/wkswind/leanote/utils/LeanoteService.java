package com.wkswind.leanote.utils;

import com.wkswind.leanote.database.User;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016-12-2.
 */

public interface LeanoteService {
    @GET("auth/login")
    Observable<User> login(@Query("email") String email, @Query("pwd") String pwd);
}
