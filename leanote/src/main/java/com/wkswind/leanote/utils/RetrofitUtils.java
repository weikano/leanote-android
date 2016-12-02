package com.wkswind.leanote.utils;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.wkswind.leanote.account.LeanoteAccount;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016-12-2.
 */

public class RetrofitUtils {
    private static final String URL = "https://leanote.com/api/";
    private static Retrofit newRetrofit(){
        CallAdapter.Factory callFactory = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
        Converter.Factory convertFactory = GsonConverterFactory.create();
        return new Retrofit.Builder().addCallAdapterFactory(callFactory).addConverterFactory(convertFactory).baseUrl(URL).build();
    }

    public static Observable<LeanoteAccount> login(String email, String pwd) {
        return newRetrofit().create(LeanoteService.class).login(email,pwd);
    }
}
