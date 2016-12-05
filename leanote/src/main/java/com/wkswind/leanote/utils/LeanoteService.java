package com.wkswind.leanote.utils;

import com.wkswind.leanote.account.LeanoteAccount;
import com.wkswind.leanote.database.Note;
import com.wkswind.leanote.database.Notebook;
import com.wkswind.leanote.database.SyncState;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016-12-2.
 */

public interface LeanoteService {
    @FormUrlEncoded
    @POST("auth/login")
    Observable<LeanoteAccount> rxLogin(@Field("email") String email, @Field("pwd") String pwd);
    @FormUrlEncoded
    @POST("auth/login")
    Call<LeanoteAccount> login(@Field("email") String email, @Field("pwd") String pwd);

    /**
     * 获取笔记，不包含content信息
     * @param token
     * @param maxEntry
     * @return
     */
    @FormUrlEncoded
    @POST("note/getSyncNotes")
    Observable<List<Note>> getNoteList(@Field("token") String token, @Field("maxEntry") int maxEntry);

    /**
     * 获取Sync信息
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("user/getSyncState")
    Observable<SyncState> getSyncState(@Field("token") String token);

    /**
     * 根据noteId拉取note信息
     * @param token
     * @param noteId
     * @return
     */
    @FormUrlEncoded
    @POST("note/getNoteAndContent")
    Observable<Note> getNoteByNoteId(@Field("token") String token,@Field("noteId") String noteId);
    @FormUrlEncoded
    @POST("notebook/getNotebooks")
    Observable<List<Notebook>> getNoteBooks(@Field("token") String token, @Field("maxEntry") int maxEntry);
}
