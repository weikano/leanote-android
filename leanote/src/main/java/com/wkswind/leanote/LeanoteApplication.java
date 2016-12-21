package com.wkswind.leanote;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;
import com.wkswind.leanote.database.DaoMaster;
import com.wkswind.leanote.database.DaoSession;
import com.wkswind.leanote.database.SqlHelper;
import com.wkswind.leanote.database.SqlUtils;
import com.wkswind.leanote.utils.RetrofitUtils;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.concurrent.locks.ReentrantLock;

import retrofit2.Retrofit;
import timber.log.Timber;

public class LeanoteApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        newSession();
        //LeakCanary
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        RetrofitUtils.init(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportTree());
        }
        QueryBuilder.LOG_SQL = BuildConfig.DEBUG;
        QueryBuilder.LOG_VALUES = BuildConfig.DEBUG;
    }

    public static DaoSession getSession() {
        try {
            lock.lock();
            return session;
        }finally {
            lock.unlock();
        }
    }

    private static DaoSession session;
    private static ReentrantLock lock = new ReentrantLock(true);

    private DaoSession newSession() {
        if (session == null) {
            lock.lock();
            if (session == null) {
                SqlHelper helper = new SqlHelper(this);
                session = new DaoMaster(helper.getWritableDb()).newSession();
            }
            lock.unlock();
        }
        return session;
    }

    private static class CrashReportTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (priority <= Log.WARN) {
                return;
            }
            Timber.log(priority, message, t);
        }
    }

}
