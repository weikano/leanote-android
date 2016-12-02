package com.wkswind.leanote.database;

import android.content.Context;

/**
 * Created by Administrator on 2016-12-2.
 */

public class Utils {
    private static DaoSession session;
    public static DaoSession newSession(Context context){
        if(session == null) {
            synchronized (Utils.class){
                if(session == null){
                    SqlHelper helper = new SqlHelper(context.getApplicationContext());
                    session = new DaoMaster(helper.getWritableDb()).newSession();
                }
            }
        }
        return session;
    }
}
