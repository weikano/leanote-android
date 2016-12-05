package com.wkswind.leanote.utils;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.wkswind.leanote.account.LeanoteAccount;
import com.wkswind.leanote.database.Note;
import com.wkswind.leanote.database.Notebook;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016-12-2.
 */

public class RetrofitUtils {
    private static Retrofit instance;
    public static void init(Context context){
        if(instance == null){
            synchronized (RetrofitUtils.class){
                if(instance == null){
                    CallAdapter.Factory callFactory = RxJava2CallAdapterFactory.create();
                    Converter.Factory convertFactory = GsonConverterFactory.create();
                    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new TokenInterceptor(context)).build();
                    instance = new Retrofit.Builder().client(client).addCallAdapterFactory(callFactory).addConverterFactory(convertFactory).baseUrl(Utils.getHost()).build();
                }
            }
        }
    }

    public static Observable<LeanoteAccount> rxLogin(String email, String pwd) {
        return instance.create(LeanoteService.class).rxLogin(email,pwd);
    }

    public static Call<LeanoteAccount> login(String email, String pwd){
        return instance.create(LeanoteService.class).login(email, pwd);
    }

    public static Observable<List<Note>> syncNote(String token, int maxEntry){
        return instance.create(LeanoteService.class).getNoteList(token, maxEntry);
    }
}
