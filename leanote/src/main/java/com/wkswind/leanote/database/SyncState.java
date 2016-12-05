package com.wkswind.leanote.database;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016-12-5.
 */

public class SyncState implements Parcelable {
    private long LastSyncTime;
    private int LastSyncUsn;

    public long getLastSyncTime() {
        return LastSyncTime;
    }

    public void setLastSyncTime(long lastSyncTime) {
        LastSyncTime = lastSyncTime;
    }

    public int getLastSyncUsn() {
        return LastSyncUsn;
    }

    public void setLastSyncUsn(int lastSyncUsn) {
        LastSyncUsn = lastSyncUsn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.LastSyncTime);
        dest.writeInt(this.LastSyncUsn);
    }

    public SyncState() {
    }

    protected SyncState(Parcel in) {
        this.LastSyncTime = in.readLong();
        this.LastSyncUsn = in.readInt();
    }

    public static final Parcelable.Creator<SyncState> CREATOR = new Parcelable.Creator<SyncState>() {
        @Override
        public SyncState createFromParcel(Parcel source) {
            return new SyncState(source);
        }

        @Override
        public SyncState[] newArray(int size) {
            return new SyncState[size];
        }
    };
}
