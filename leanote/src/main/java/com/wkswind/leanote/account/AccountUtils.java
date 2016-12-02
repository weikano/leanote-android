package com.wkswind.leanote.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Build;

import com.wkswind.leanote.BuildConfig;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by Administrator on 2016-12-2.
 */

public class AccountUtils {

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
}
