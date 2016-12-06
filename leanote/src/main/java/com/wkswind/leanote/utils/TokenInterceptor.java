package com.wkswind.leanote.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

import com.google.gson.Gson;
import com.wkswind.leanote.BuildConfig;
import com.wkswind.leanote.account.LeanoteAccount;
import com.wkswind.leanote.base.LeanoteResponseBody;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import timber.log.Timber;

/**
 * 每次请求后都会根据返回值判断是否需要重新登录获取Token
 */
class TokenInterceptor implements Interceptor {
    private final Context context;
    private final AccountManager am;
    private final String packageName;
    TokenInterceptor(Context context){
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
        if(!isLoginRequest(request) && isTokenInvalid(response)){
            Account[] accounts = am.getAccountsByTypeForPackage(BuildConfig.ACCOUNT_TYPE, packageName);
            Account account = accounts[0];
            final String pwd = am.getPassword(account);
            Response login = RetrofitUtils.loginNormal(account.name,Utils.decrypt(pwd)).execute();
            Gson gson = new Gson();
            LeanoteAccount auth = gson.fromJson(login.body().string(), LeanoteAccount.class);
            auth.saveToAccountManager(context, pwd, false);
//            login.body().toString();
//            Call<LeanoteAccount> call = RetrofitUtils.login(account.name, Utils.decrypt(pwd));
//            LeanoteAccount auth = call.execute().body();
//            auth.saveToAccountManager(context, pwd, false);

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
        return response;
    }

    private boolean isLoginRequest(Request request) {
        return request.url().toString().contains("auth/login");
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
