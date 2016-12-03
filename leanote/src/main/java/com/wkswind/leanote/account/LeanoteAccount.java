package com.wkswind.leanote.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.wkswind.leanote.BuildConfig;

/**
 * Created by Administrator on 2016-12-2.
 */

public class LeanoteAccount implements Parcelable {

    private String UserId;
    private String Email;
    private String Username;
    private String Token;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        this.UserId = userId;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        this.Token = token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.UserId);
        dest.writeString(this.Email);
        dest.writeString(this.Username);
        dest.writeString(this.Token);
    }

    public LeanoteAccount() {
    }

    protected LeanoteAccount(Parcel in) {
        this.UserId = in.readString();
        this.Email = in.readString();
        this.Username = in.readString();
        this.Token = in.readString();
    }

    public static final Parcelable.Creator<LeanoteAccount> CREATOR = new Parcelable.Creator<LeanoteAccount>() {
        @Override
        public LeanoteAccount createFromParcel(Parcel source) {
            return new LeanoteAccount(source);
        }

        @Override
        public LeanoteAccount[] newArray(int size) {
            return new LeanoteAccount[size];
        }
    };

    @Override
    public String toString() {
        return "LeanoteAccount{" +
                "UserId='" + UserId + '\'' +
                ", Email='" + Email + '\'' +
                ", Username='" + Username + '\'' +
                ", Token='" + Token + '\'' +
                '}';
    }

    public Intent saveToAccountManager(Context context, String password, boolean addAccount){
        final Intent intent = new Intent();
        final AccountManager am = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        final Account account = new Account(Email, BuildConfig.ACCOUNT_TYPE);
        if(addAccount){
            final Bundle userData = new Bundle();
            userData.putString(AccountUtils.KEY_USER_ID, UserId);
            userData.putString(AccountUtils.KEY_EMAIL, Email);
            userData.putString(AccountUtils.KEY_USER_NAME, Username);
            am.addAccountExplicitly(account, password, userData);
        }else{
            am.invalidateAuthToken(BuildConfig.ACCOUNT_TYPE, Token);
            am.setPassword(account,password);
        }
        intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, Email);
        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE,BuildConfig.ACCOUNT_TYPE);
        intent.putExtra(AccountManager.KEY_AUTHTOKEN, Token);
        return intent;
    }
}
