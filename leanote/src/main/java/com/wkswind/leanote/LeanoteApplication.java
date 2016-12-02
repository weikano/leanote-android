package com.wkswind.leanote;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.wkswind.leanote.database.DaoSession;
import com.wkswind.leanote.database.SqlHelper;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.identityscope.IdentityScopeType;

/**
 * Created by Administrator on 2016-12-2.
 */

public class LeanoteApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //LeakCanary
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

}
