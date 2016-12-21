package com.wkswind.leanote.utils;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.wkswind.leanote.account.LeanoteAccount;
import com.wkswind.leanote.database.Note;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {
    private static OkHttpClient.Builder defaultClientBuilder(){
        return new OkHttpClient.Builder();
    }

    private static Retrofit instance;
    public static void init(Context context){
        if(instance == null){
            synchronized (RetrofitUtils.class){
                if(instance == null){
                    CallAdapter.Factory callFactory = RxJava2CallAdapterFactory.create();
                    Converter.Factory convertFactory = GsonConverterFactory.create();
                    OkHttpClient.Builder builder = defaultClientBuilder();
                    builder.interceptors().add(new TokenInterceptor(context));
                    instance = new Retrofit.Builder().client(builder.build()).addCallAdapterFactory(callFactory).addConverterFactory(convertFactory).baseUrl(Utils.getHost()).build();
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

    static okhttp3.Call loginNormal(String email, String pwd){
        FormBody body = new FormBody.Builder().add("email",email).add("pwd",pwd).build();
        HttpUrl host = HttpUrl.parse(Utils.getHost());
        List<String> segs = host.pathSegments();
        HttpUrl.Builder builder  = new HttpUrl.Builder().scheme(host.scheme()).host(host.host());
        for (int i = 0; i < segs.size(); i++) {
            builder.addPathSegment(segs.get(i));
        }
        builder.addPathSegment("auth").addPathSegment("login");
        Request request = new Request.Builder().url(builder.build()).post(body).build();
        return defaultClientBuilder().build().newCall(request);
    }

    public static Observable<List<Note>> syncNote(String token, int maxEntry){
        return instance.create(LeanoteService.class).getNoteList(token, maxEntry);
    }

}
