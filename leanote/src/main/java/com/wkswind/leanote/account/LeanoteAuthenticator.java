package com.wkswind.leanote.account;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.wkswind.leanote.utils.RetrofitUtils;

import org.reactivestreams.Subscriber;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by Administrator on 2016-12-2.
 */

public class LeanoteAuthenticator extends AbstractAccountAuthenticator {
    private Context context;
    private AccountManager am;
    public LeanoteAuthenticator(Context context) {
        super(context);
        this.context = context;
        am = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String s) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse accountAuthenticatorResponse, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        final Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(AccountUtils.KEY_ACCOUNT_TYPE, accountType);
        intent.putExtra(AccountUtils.KEY_AUTH_TYPE, authTokenType);
        intent.putExtra(AccountUtils.KEY_ADD_ACCOUNT, true);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, accountAuthenticatorResponse);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        String authToken = am.peekAuthToken(account, authTokenType);
        if(TextUtils.isEmpty(authToken)){
            final String pwd = am.getPassword(account);
            try {
                Response<LeanoteAccount> response = RetrofitUtils.login(account.name, pwd).execute();
                authToken = response.body().getToken();
                if(!TextUtils.isEmpty(authToken)){
                    final Bundle result = new Bundle();
                    result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
                    result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
                    result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
                    return result;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
//            RetrofitUtils.rxLogin
        }
        //如果获取不到token，重新登录
        final Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, accountAuthenticatorResponse);
        intent.putExtra(AccountUtils.KEY_ACCOUNT_TYPE, account.type);
        intent.putExtra(AccountUtils.KEY_AUTH_TYPE, authTokenType);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public String getAuthTokenLabel(String s) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String s, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String[] strings) throws NetworkErrorException {
        return null;
    }
}
