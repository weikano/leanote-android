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
public class Media implements Parcelable {
    @Id
    private String id;
    private String noteId;
    private String filePath;
    private String fileName;
    private String title;
    private String fileUrl;
    private String thumbnailUrl;
    private String uploadState;
    private String description;
    private String caption;
    private int alignment;
    private int width;
    private int height;
    private String mimeType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getUploadState() {
        return uploadState;
    }

    public void setUploadState(String uploadState) {
        this.uploadState = uploadState;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getAlignment() {
        return alignment;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.noteId);
        dest.writeString(this.filePath);
        dest.writeString(this.fileName);
        dest.writeString(this.title);
        dest.writeString(this.fileUrl);
        dest.writeString(this.thumbnailUrl);
        dest.writeString(this.uploadState);
        dest.writeString(this.description);
        dest.writeString(this.caption);
        dest.writeInt(this.alignment);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeString(this.mimeType);
    }

    public Media() {
    }

    protected Media(Parcel in) {
        this.id = in.readString();
        this.noteId = in.readString();
        this.filePath = in.readString();
        this.fileName = in.readString();
        this.title = in.readString();
        this.fileUrl = in.readString();
        this.thumbnailUrl = in.readString();
        this.uploadState = in.readString();
        this.description = in.readString();
        this.caption = in.readString();
        this.alignment = in.readInt();
        this.width = in.readInt();
        this.height = in.readInt();
        this.mimeType = in.readString();
    }

    @Generated(hash = 879357524)
    public Media(String id, String noteId, String filePath, String fileName, String title,
            String fileUrl, String thumbnailUrl, String uploadState, String description,
            String caption, int alignment, int width, int height, String mimeType) {
        this.id = id;
        this.noteId = noteId;
        this.filePath = filePath;
        this.fileName = fileName;
        this.title = title;
        this.fileUrl = fileUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.uploadState = uploadState;
        this.description = description;
        this.caption = caption;
        this.alignment = alignment;
        this.width = width;
        this.height = height;
        this.mimeType = mimeType;
    }

    public static final Parcelable.Creator<Media> CREATOR = new Parcelable.Creator<Media>() {
        @Override
        public Media createFromParcel(Parcel source) {
            return new Media(source);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };
}
