package com.wkswind.leanote.base;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.OnAccountsUpdateListener;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import timber.log.Timber;

/**
 * Created by Administrator on 2016-12-3.
 */

public class BaseActivity extends AppCompatActivity implements OnAccountsUpdateListener {
    private AccountManager am;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        am = (AccountManager) getSystemService(ACCOUNT_SERVICE);
        am.addOnAccountsUpdatedListener(this, null, false);
    }

    @Override
    public void onAccountsUpdated(Account[] accounts) {
        if(accounts != null){
            List<Account> list = Arrays.asList(accounts);
            Timber.i(list.toString());
        }else {
            Timber.i("No Account");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        am.removeOnAccountsUpdatedListener(this);
    }
}
