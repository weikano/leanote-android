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
public class Note implements Parcelable {
    @Id
    private String noteId;
    private String notebookId;
    private String userId;
    private String title;
    private String tags;
    private String content;
    private boolean markdown;
    private boolean blog;
    private boolean trash;
    private String files;
    private long createdTime;
    private long updatedTime;
    private long publishTime;
    private int usn;
    private String desc;
    private String abstractNote;
    private boolean dirty;
    private boolean uploading;

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getNotebookId() {
        return notebookId;
    }

    public void setNotebookId(String notebookId) {
        this.notebookId = notebookId;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isMarkdown() {
        return markdown;
    }

    public void setMarkdown(boolean markdown) {
        this.markdown = markdown;
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

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
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

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public int getUsn() {
        return usn;
    }

    public void setUsn(int usn) {
        this.usn = usn;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAbstractNote() {
        return abstractNote;
    }

    public void setAbstractNote(String abstractNote) {
        this.abstractNote = abstractNote;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isUploading() {
        return uploading;
    }

    public void setUploading(boolean uploading) {
        this.uploading = uploading;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.noteId);
        dest.writeString(this.notebookId);
        dest.writeString(this.userId);
        dest.writeString(this.title);
        dest.writeString(this.tags);
        dest.writeString(this.content);
        dest.writeByte(this.markdown ? (byte) 1 : (byte) 0);
        dest.writeByte(this.blog ? (byte) 1 : (byte) 0);
        dest.writeByte(this.trash ? (byte) 1 : (byte) 0);
        dest.writeString(this.files);
        dest.writeLong(this.createdTime);
        dest.writeLong(this.updatedTime);
        dest.writeLong(this.publishTime);
        dest.writeInt(this.usn);
        dest.writeString(this.desc);
        dest.writeString(this.abstractNote);
        dest.writeByte(this.dirty ? (byte) 1 : (byte) 0);
        dest.writeByte(this.uploading ? (byte) 1 : (byte) 0);
    }

    public boolean getMarkdown() {
        return this.markdown;
    }

    public boolean getBlog() {
        return this.blog;
    }

    public boolean getTrash() {
        return this.trash;
    }

    public boolean getDirty() {
        return this.dirty;
    }

    public boolean getUploading() {
        return this.uploading;
    }

    public Note() {
    }

    protected Note(Parcel in) {
        this.noteId = in.readString();
        this.notebookId = in.readString();
        this.userId = in.readString();
        this.title = in.readString();
        this.tags = in.readString();
        this.content = in.readString();
        this.markdown = in.readByte() != 0;
        this.blog = in.readByte() != 0;
        this.trash = in.readByte() != 0;
        this.files = in.readString();
        this.createdTime = in.readLong();
        this.updatedTime = in.readLong();
        this.publishTime = in.readLong();
        this.usn = in.readInt();
        this.desc = in.readString();
        this.abstractNote = in.readString();
        this.dirty = in.readByte() != 0;
        this.uploading = in.readByte() != 0;
    }

    @Generated(hash = 216805632)
    public Note(String noteId, String notebookId, String userId, String title, String tags,
            String content, boolean markdown, boolean blog, boolean trash, String files,
            long createdTime, long updatedTime, long publishTime, int usn, String desc,
            String abstractNote, boolean dirty, boolean uploading) {
        this.noteId = noteId;
        this.notebookId = notebookId;
        this.userId = userId;
        this.title = title;
        this.tags = tags;
        this.content = content;
        this.markdown = markdown;
        this.blog = blog;
        this.trash = trash;
        this.files = files;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.publishTime = publishTime;
        this.usn = usn;
        this.desc = desc;
        this.abstractNote = abstractNote;
        this.dirty = dirty;
        this.uploading = uploading;
    }

    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
