package app.atr.mobitribe.com.app_time_recorder.jobscheduler;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import java.util.concurrent.TimeUnit;

import app.atr.mobitribe.com.app_time_recorder.backgroundServices.LocationService;

/**
 * Author: Uzair Qureshi
 * Date:  7/10/17.
 * Description:
 */

public class LocationJob extends Job {
    public static final String TAG="locationJob";
    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        startService(getContext());

        return Result.SUCCESS;
    }
    private void startService(Context context)
    {
        context.startService(new Intent(context, LocationService.class));


    }
    public static void scheduleJob() {
        new JobRequest.Builder(LocationJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(1))
                .setUpdateCurrent(true)//this request will cancel any preexisting job with the same tag while being scheduled.?true :false
                .setPersisted(true)
                .build()
                .schedule();
    }

    @Override
    protected ComponentName startWakefulService(@NonNull Intent intent) {
        return super.startWakefulService(intent);
    }
}
