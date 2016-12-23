package com.wkswind.leanote.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.wkswind.leanote.gson.CustomTypeAdapter;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@JsonAdapter(value = CustomTypeAdapter.NotebookTypeAdapter.class)
@Entity
public class Notebook implements Parcelable {
    @Id(autoincrement = true)
    @Expose(deserialize = false, serialize = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String NotebookId;

    private String ParentNotebookId;
    private int Seq;
    private String UserId;
    private String Title;
    private String UrlTitle;
    private boolean IsBlog;
    private boolean trash;
    private boolean IsDeleted;
    @Expose
    private long CreatedTime;
    @Expose
    private long UpdatedTime;
    private int Usn;
    private boolean dirty;

    public String getNotebookId() {
        return NotebookId;
    }

    public void setNotebookId(String notebookId) {
        NotebookId = notebookId;
    }

    public String getParentNotebookId() {
        return ParentNotebookId;
    }

    public void setParentNotebookId(String parentNotebookId) {
        ParentNotebookId = parentNotebookId;
    }

    public int getSeq() {
        return Seq;
    }

    public void setSeq(int seq) {
        Seq = seq;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getUrlTitle() {
        return UrlTitle;
    }

    public void setUrlTitle(String urlTitle) {
        UrlTitle = urlTitle;
    }

    public boolean isBlog() {
        return IsBlog;
    }

    public void setBlog(boolean blog) {
        IsBlog = blog;
    }

    public boolean isTrash() {
        return trash;
    }

    public void setTrash(boolean trash) {
        this.trash = trash;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }

    public long getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(long createdTime) {
        CreatedTime = createdTime;
    }

    public long getUpdatedTime() {
        return UpdatedTime;
    }

    public void setUpdatedTime(long updatedTime) {
        UpdatedTime = updatedTime;
    }

    public int getUsn() {
        return Usn;
    }

    public void setUsn(int usn) {
        Usn = usn;
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
        dest.writeLong(getId());
        dest.writeString(this.NotebookId);
        dest.writeString(this.ParentNotebookId);
        dest.writeInt(this.Seq);
        dest.writeString(this.UserId);
        dest.writeString(this.Title);
        dest.writeString(this.UrlTitle);
        dest.writeByte(this.IsBlog ? (byte) 1 : (byte) 0);
        dest.writeByte(this.trash ? (byte) 1 : (byte) 0);
        dest.writeByte(this.IsDeleted ? (byte) 1 : (byte) 0);
        dest.writeLong(this.CreatedTime);
        dest.writeLong(this.UpdatedTime);
        dest.writeInt(this.Usn);
        dest.writeByte(this.dirty ? (byte) 1 : (byte) 0);
    }

    public boolean getIsBlog() {
        return this.IsBlog;
    }

    public void setIsBlog(boolean IsBlog) {
        this.IsBlog = IsBlog;
    }

    public boolean getTrash() {
        return this.trash;
    }

    public boolean getIsDeleted() {
        return this.IsDeleted;
    }

    public void setIsDeleted(boolean IsDeleted) {
        this.IsDeleted = IsDeleted;
    }

    public boolean getDirty() {
        return this.dirty;
    }


    public Notebook() {
    }

    protected Notebook(Parcel in) {
        setId(in.readLong());
        this.NotebookId = in.readString();
        this.ParentNotebookId = in.readString();
        this.Seq = in.readInt();
        this.UserId = in.readString();
        this.Title = in.readString();
        this.UrlTitle = in.readString();
        this.IsBlog = in.readByte() != 0;
        this.trash = in.readByte() != 0;
        this.IsDeleted = in.readByte() != 0;
        this.CreatedTime = in.readLong();
        this.UpdatedTime = in.readLong();
        this.Usn = in.readInt();
        this.dirty = in.readByte() != 0;
    }

    @Generated(hash = 1565693410)
    public Notebook(Long id, String NotebookId, String ParentNotebookId, int Seq, String UserId,
            String Title, String UrlTitle, boolean IsBlog, boolean trash, boolean IsDeleted,
            long CreatedTime, long UpdatedTime, int Usn, boolean dirty) {
        this.id = id;
        this.NotebookId = NotebookId;
        this.ParentNotebookId = ParentNotebookId;
        this.Seq = Seq;
        this.UserId = UserId;
        this.Title = Title;
        this.UrlTitle = UrlTitle;
        this.IsBlog = IsBlog;
        this.trash = trash;
        this.IsDeleted = IsDeleted;
        this.CreatedTime = CreatedTime;
        this.UpdatedTime = UpdatedTime;
        this.Usn = Usn;
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
