package com.wkswind.leanote.database;

import android.test.AndroidTestCase;

import com.google.gson.Gson;
import com.wkswind.leanote.utils.Utils;

import org.junit.Test;

import timber.log.Timber;

/**
 * Created by Administrator on 2016-12-2.
 */
public class UtilsTest {
    @Test
    public void testNewSession() throws Exception {
        Note note = new Note();
        note.setNoteId("noteId");
        note.setNotebookId("notebookId");
        note.setUserId("userId");
//        assertEquals(SqlUtils.newSession(mContext).getNoteDao().insert(note)>0, true);
    }

    @Test
    public void gson() {
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
        Timber.i(gson.toJson(note));
    }

}