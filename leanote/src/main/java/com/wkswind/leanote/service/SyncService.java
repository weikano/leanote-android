package com.wkswind.leanote.service;

import android.app.job.JobParameters;
import android.app.job.JobService;

/**
 * Created by Administrator on 2016-12-2.
 */

/**
 * 同步
 */
public class SyncService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
