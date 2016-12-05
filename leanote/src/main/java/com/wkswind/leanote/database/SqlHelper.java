package com.wkswind.leanote.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.greenrobot.greendao.database.DatabaseOpenHelper;

/**
 * Created by Administrator on 2016-12-2.
 */

public class SqlHelper extends DatabaseOpenHelper {
    public SqlHelper(Context context) {
        super(context, "leanote.db", 3);
    }
}
