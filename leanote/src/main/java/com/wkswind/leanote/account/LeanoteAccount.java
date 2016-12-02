package com.wkswind.leanote.account;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016-12-2.
 */

public class LeanoteAccount implements Parcelable {

    private String userId;
    private String email;
    private String username;
    private String token;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.email);
        dest.writeString(this.username);
        dest.writeString(this.token);
    }

    public LeanoteAccount() {
    }

    protected LeanoteAccount(Parcel in) {
        this.userId = in.readString();
        this.email = in.readString();
        this.username = in.readString();
        this.token = in.readString();
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
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
