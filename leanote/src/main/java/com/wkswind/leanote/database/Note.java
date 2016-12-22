package com.wkswind.leanote.database;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.JsonAdapter;
import com.wkswind.leanote.base.BaseEntity;
import com.wkswind.leanote.base.ContentComparator;
import com.wkswind.leanote.gson.CustomTypeAdapter;
import com.wkswind.leanote.gson.String2Array;
import com.wkswind.leanote.gson.UTC2Long;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Comparator;

@JsonAdapter(CustomTypeAdapter.NoteTypeAdapter.class)
@Entity
public class Note extends BaseEntity implements Parcelable, ContentComparator<Note> {
    private String NoteId;
    private String NotebookId;
    private String UserId;
    private String Title;
    private String Desc;
    @String2Array
    private String Tags;
    private String Abstract;
    private String Content;

    private boolean IsMarkdown;
    private boolean IsBlog;
    private boolean IsTrash;
    private boolean IsDeleted;
    private int Usn;
    @String2Array
    private String Files;
    @UTC2Long
    private long CreatedTime;
    @UTC2Long
    private long UpdatedTime;
    @UTC2Long
    private long PublicTime;
    private boolean dirty;
    private boolean uploading;

    public String getNoteId() {
        return NoteId;
    }

    public void setNoteId(String noteId) {
        NoteId = noteId;
    }

    public String getNotebookId() {
        return NotebookId;
    }

    public void setNotebookId(String notebookId) {
        NotebookId = notebookId;
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

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getTags() {
        return Tags;
    }

    public void setTags(String tags) {
        Tags = tags;
    }

    public String getAbstract() {
        return Abstract;
    }

    public void setAbstract(String anAbstract) {
        Abstract = anAbstract;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public boolean isMarkdown() {
        return IsMarkdown;
    }

    public void setMarkdown(boolean markdown) {
        IsMarkdown = markdown;
    }

    public boolean isBlog() {
        return IsBlog;
    }

    public void setBlog(boolean blog) {
        IsBlog = blog;
    }

    public boolean isTrash() {
        return IsTrash;
    }

    public void setTrash(boolean trash) {
        IsTrash = trash;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }

    public int getUsn() {
        return Usn;
    }

    public void setUsn(int usn) {
        Usn = usn;
    }

    public String getFiles() {
        return Files;
    }

    public void setFiles(String files) {
        Files = files;
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

    public long getPublicTime() {
        return PublicTime;
    }

    public void setPublicTime(long publicTime) {
        PublicTime = publicTime;
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
        dest.writeLong(getId());
        dest.writeString(this.NoteId);
        dest.writeString(this.NotebookId);
        dest.writeString(this.UserId);
        dest.writeString(this.Title);
        dest.writeString(this.Desc);
        dest.writeString(this.Tags);
        dest.writeString(this.Abstract);
        dest.writeString(this.Content);
        dest.writeByte(this.IsMarkdown ? (byte) 1 : (byte) 0);
        dest.writeByte(this.IsBlog ? (byte) 1 : (byte) 0);
        dest.writeByte(this.IsTrash ? (byte) 1 : (byte) 0);
        dest.writeByte(this.IsDeleted ? (byte) 1 : (byte) 0);
        dest.writeInt(this.Usn);
        dest.writeString(this.Files);
        dest.writeLong(this.CreatedTime);
        dest.writeLong(this.UpdatedTime);
        dest.writeLong(this.PublicTime);
        dest.writeByte(this.dirty ? (byte) 1 : (byte) 0);
        dest.writeByte(this.uploading ? (byte) 1 : (byte) 0);
    }

    public boolean getIsMarkdown() {
        return this.IsMarkdown;
    }

    public void setIsMarkdown(boolean IsMarkdown) {
        this.IsMarkdown = IsMarkdown;
    }

    public boolean getIsBlog() {
        return this.IsBlog;
    }

    public void setIsBlog(boolean IsBlog) {
        this.IsBlog = IsBlog;
    }

    public boolean getIsTrash() {
        return this.IsTrash;
    }

    public void setIsTrash(boolean IsTrash) {
        this.IsTrash = IsTrash;
    }

    public boolean getIsDeleted() {
        return this.IsDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.IsDeleted = isDeleted;
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
        setId(in.readLong());
        this.NoteId = in.readString();
        this.NotebookId = in.readString();
        this.UserId = in.readString();
        this.Title = in.readString();
        this.Desc = in.readString();
        this.Tags = in.readString();
        this.Abstract = in.readString();
        this.Content = in.readString();
        this.IsMarkdown = in.readByte() != 0;
        this.IsBlog = in.readByte() != 0;
        this.IsTrash = in.readByte() != 0;
        this.IsDeleted = in.readByte() != 0;
        this.Usn = in.readInt();
        this.Files = in.readString();
        this.CreatedTime = in.readLong();
        this.UpdatedTime = in.readLong();
        this.PublicTime = in.readLong();
        this.dirty = in.readByte() != 0;
        this.uploading = in.readByte() != 0;
    }

    @Generated(hash = 1239938208)
    public Note(Long id, String NoteId, String NotebookId, String UserId, String Title, String Desc, String Tags,
            String Abstract, String Content, boolean IsMarkdown, boolean IsBlog, boolean IsTrash, boolean IsDeleted,
            int Usn, String Files, long CreatedTime, long UpdatedTime, long PublicTime, boolean dirty, boolean uploading) {
        setId(id);
        this.NoteId = NoteId;
        this.NotebookId = NotebookId;
        this.UserId = UserId;
        this.Title = Title;
        this.Desc = Desc;
        this.Tags = Tags;
        this.Abstract = Abstract;
        this.Content = Content;
        this.IsMarkdown = IsMarkdown;
        this.IsBlog = IsBlog;
        this.IsTrash = IsTrash;
        this.IsDeleted = IsDeleted;
        this.Usn = Usn;
        this.Files = Files;
        this.CreatedTime = CreatedTime;
        this.UpdatedTime = UpdatedTime;
        this.PublicTime = PublicTime;
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

    @Override
    public boolean areContentsTheSame(@NonNull  Note item) {
        return Title.equals(item.getTitle()) && Content.equals(item.getContent()) && UpdatedTime == item.getUpdatedTime();
    }

    @Override
    public Comparator<Note> getDefaultComparator() {
        return new Comparator<Note>() {
            @Override
            public int compare(Note o1, Note o2) {
                return (int) (o1.getUpdatedTime() - o2.getUpdatedTime());
            }
        };
    }


}
