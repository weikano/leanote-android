package com.wkswind.leanote.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;

import com.wkswind.leanote.BuildConfig;
import com.wkswind.leanote.utils.Utils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;

public class AccountUtils {

    static final String KEY_USER_ID = Utils.generateKeyPrefix(AccountUtils.class) + "KEY_USER_ID";
    static final String KEY_USER_NAME = Utils.generateKeyPrefix(AccountUtils.class) + "KEY_USER_NAME";
    static final String KEY_EMAIL = Utils.generateKeyPrefix(AccountUtils.class) + "KEY_EMAIL";

    static final String KEY_ACCOUNT_TYPE = Utils.generateKeyPrefix(AccountUtils.class)+"KEY_ACCOUNT_TYPE";
    static final String KEY_AUTH_TYPE = Utils.generateKeyPrefix(AccountUtils.class)+"KEY_AUTH_TYPE";
    static final String KEY_ADD_ACCOUNT = Utils.generateKeyPrefix(AccountUtils.class) + "KEY_ADD_ACCOUNT";

    public static Observable<Account[]> getAccount(final Context context){
        return Observable.create(new ObservableOnSubscribe<Account[]>() {
            @Override
            public void subscribe(ObservableEmitter<Account[]> e) throws Exception {
                AccountManager am = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
                Account[] accounts = am.getAccountsByTypeForPackage(BuildConfig.ACCOUNT_TYPE, context.getPackageName());
                e.onNext(accounts);
                e.onComplete();
            }
        });
    }

    public static Observable<Boolean> hasAccount(final Context context){
        return getAccount(context).map(new Function<Account[], Boolean>() {
            @Override
            public Boolean apply(Account[] accounts) throws Exception {
                return accounts != null && accounts.length == 1;
            }
        });
    }

    public static void enterLogin(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }




}
