package com.wkswind.leanote.account;

import android.accounts.AbstractAccountAuthenticator;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2016-12-2.
 */

public class LeanoteAuthenticatorService extends Service {
    private AbstractAccountAuthenticator authenticator;
    @Override
    public void onCreate() {
        super.onCreate();
        authenticator = new LeanoteAuthenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return authenticator.getIBinder();
    }
}
