package com.wkswind.leanote;

import android.app.Application;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;
import com.wkswind.leanote.database.DaoSession;
import com.wkswind.leanote.database.SqlHelper;
import com.wkswind.leanote.utils.RetrofitUtils;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.identityscope.IdentityScopeType;

import retrofit2.Retrofit;
import timber.log.Timber;

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
        RetrofitUtils.init(this);
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }else {
            Timber.plant(new CrashReportTree());
        }
    }

    private static class CrashReportTree extends Timber.Tree {

        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if(priority <= Log.WARN){
                return;
            }
            Timber.log(priority, message, t);
        }
    }

}
