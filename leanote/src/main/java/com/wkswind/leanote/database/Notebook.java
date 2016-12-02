package com.wkswind.leanote.database;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2016-12-2.
 */
@Entity
public class Notebook implements Parcelable {
    @Id
    private String id;
    private String pid;
    private String userId;
    private String title;
    private String urlTitle;
    private boolean blog;
    private boolean trash;
    private boolean deleted;
    private long createdTime;
    private long updatedTime;
    private int usn;
    private boolean dirty;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlTitle() {
        return urlTitle;
    }

    public void setUrlTitle(String urlTitle) {
        this.urlTitle = urlTitle;
    }

    public boolean isBlog() {
        return blog;
    }

    public void setBlog(boolean blog) {
        this.blog = blog;
    }

    public boolean isTrash() {
        return trash;
    }

    public void setTrash(boolean trash) {
        this.trash = trash;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public long getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(long updatedTime) {
        this.updatedTime = updatedTime;
    }

    public int getUsn() {
        return usn;
    }

    public void setUsn(int usn) {
        this.usn = usn;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.pid);
        dest.writeString(this.userId);
        dest.writeString(this.title);
        dest.writeString(this.urlTitle);
        dest.writeByte(this.blog ? (byte) 1 : (byte) 0);
        dest.writeByte(this.trash ? (byte) 1 : (byte) 0);
        dest.writeByte(this.deleted ? (byte) 1 : (byte) 0);
        dest.writeLong(this.createdTime);
        dest.writeLong(this.updatedTime);
        dest.writeInt(this.usn);
        dest.writeByte(this.dirty ? (byte) 1 : (byte) 0);
    }

    public boolean getBlog() {
        return this.blog;
    }

    public boolean getTrash() {
        return this.trash;
    }

    public boolean getDeleted() {
        return this.deleted;
    }

    public boolean getDirty() {
        return this.dirty;
    }

    public Notebook() {
    }

    protected Notebook(Parcel in) {
        this.id = in.readString();
        this.pid = in.readString();
        this.userId = in.readString();
        this.title = in.readString();
        this.urlTitle = in.readString();
        this.blog = in.readByte() != 0;
        this.trash = in.readByte() != 0;
        this.deleted = in.readByte() != 0;
        this.createdTime = in.readLong();
        this.updatedTime = in.readLong();
        this.usn = in.readInt();
        this.dirty = in.readByte() != 0;
    }

    @Generated(hash = 766654249)
    public Notebook(String id, String pid, String userId, String title, String urlTitle, boolean blog,
            boolean trash, boolean deleted, long createdTime, long updatedTime, int usn,
            boolean dirty) {
        this.id = id;
        this.pid = pid;
        this.userId = userId;
        this.title = title;
        this.urlTitle = urlTitle;
        this.blog = blog;
        this.trash = trash;
        this.deleted = deleted;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.usn = usn;
        this.dirty = dirty;
    }

    public static final Parcelable.Creator<Notebook> CREATOR = new Parcelable.Creator<Notebook>() {
        @Override
        public Notebook createFromParcel(Parcel source) {
            return new Notebook(source);
        }

        @Override
        public Notebook[] newArray(int size) {
            return new Notebook[size];
        }
    };
}
