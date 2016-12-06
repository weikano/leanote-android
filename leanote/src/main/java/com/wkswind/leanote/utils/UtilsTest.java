package com.wkswind.leanote.utils;

import com.google.gson.Gson;
import com.wkswind.leanote.database.Note;

import timber.log.Timber;

/**
 * Created by Administrator on 2016-12-6.
 */

public class UtilsTest {
    public static void gson(){
        Gson gson = Utils.defaultGson();
        Note note = new Note();
        note.setUserId("userId");
        note.setNotebookId("notebookId");
        note.setNoteId("noteId");
        note.setAbstract("abstract");
        note.setBlog(false);
        note.setContent("content");
        note.setCreatedTime(System.currentTimeMillis());
        note.setDeleted(true);
        note.setDesc("desc");
        note.setDirty(true);
        note.setFiles("1,2,3,4");
        note.setId(111);
        note.setIsBlog(true);
        note.setIsDeleted(true);
        note.setIsMarkdown(true);
        note.setIsTrash(true);
        note.setMarkdown(true);
        note.setTags("1,2,3");
        note.setTitle("title");
        String str = gson.toJson(note);
        Note newNote = gson.fromJson(str, Note.class);
        Timber.i(gson.toJson(note));

    }
}
