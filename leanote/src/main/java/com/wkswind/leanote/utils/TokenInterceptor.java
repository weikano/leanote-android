package com.wkswind.leanote.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wkswind.leanote.BuildConfig;
import com.wkswind.leanote.account.LeanoteAccount;
import com.wkswind.leanote.base.LeanoteResponseBody;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by Administrator on 2016-12-5.
 */

public class TokenInterceptor implements Interceptor {
    private final Context context;
    private final AccountManager am;
    private final String packageName;
    public TokenInterceptor(Context context){
        this.context = context;
        am = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        packageName = context.getPackageName();
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request request = chain.request();
        long begin = System.currentTimeMillis();
        Timber.i("Sending Request %s with body %s", request.url(), String.valueOf(request.body()));
        final Response response = chain.proceed(request);
        long end = System.currentTimeMillis();
        Timber.i("Received Response for %s cost %d", request.url(), (end - begin));
        if(isTokenInvalid(response)){
            Account[] accounts = am.getAccountsByTypeForPackage(BuildConfig.ACCOUNT_TYPE, packageName);
            Account account = accounts[0];
            final String pwd = am.getPassword(account);
            Call<LeanoteAccount> call = RetrofitUtils.login(account.name, Utils.decrypt(context, pwd));
            LeanoteAccount auth = call.execute().body();
            auth.saveToAccountManager(context, pwd, false);

            final RequestBody body = request.body();
            final FormBody.Builder builder = new FormBody.Builder();
            if(body instanceof FormBody){
                for (int i = 0; i < ((FormBody) body).size(); i++) {
                    String name = ((FormBody) body).name(i);
                    if(name.equals("token")){
                        builder.add(name, auth.getToken());
                    }else {
                        builder.add(((FormBody) body).name(i), ((FormBody) body).value(i));
                    }

                }
            }
            final Request newRequest = request.newBuilder().url(request.url()).method(request.method(),builder.build()).build();
            return chain.proceed(newRequest);
        }
        return chain.proceed(request.newBuilder().build());
    }


    private boolean isTokenInvalid(Response response) {
        Gson gson = new Gson();
        try {
            LeanoteResponseBody item = gson.fromJson(response.body().string(), LeanoteResponseBody.class);
            return (!item.isOk() && item.getMsg().equals("NOTLOGIN"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
