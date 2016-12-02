package com.wkswind.leanote.database;

import android.test.AndroidTestCase;

import org.junit.Test;

import static org.junit.Assert.*;

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
        assertEquals(Utils.newSession(mContext).getNoteDao().insert(note)>0, true);
    }

}