package com.wkswind.leanote.utils;

import com.google.gson.Gson;
import com.wkswind.leanote.database.Note;

import timber.log.Timber;

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
        note.setId((long) 111);
        note.setMarkdown(true);
        note.setTags("1,2,3");
        note.setTitle("title");
        note.setCreatedTime(System.currentTimeMillis());
        String str = gson.toJson(note);
        Timber.i(str);
        Note newNote = gson.fromJson(str, Note.class);


    }
}
