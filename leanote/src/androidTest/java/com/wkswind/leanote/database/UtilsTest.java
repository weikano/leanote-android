package com.wkswind.leanote.database;

import android.test.AndroidTestCase;

import org.junit.Test;

/**
 * Created by Administrator on 2016-12-2.
 */
public class UtilsTest extends AndroidTestCase{
    @Test
    public void testNewSession() throws Exception {
        Note note = new Note();
        note.setNoteId("noteId");
        note.setNotebookId("notebookId");
        note.setUserId("userId");
        assertEquals(SqlUtils.newSession(mContext).getNoteDao().insert(note)>0, true);
    }

}