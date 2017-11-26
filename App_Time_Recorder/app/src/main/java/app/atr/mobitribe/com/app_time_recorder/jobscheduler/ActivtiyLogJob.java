package app.atr.mobitribe.com.app_time_recorder.jobscheduler;

import android.app.Notification;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import app.atr.mobitribe.com.app_time_recorder.R;
import app.atr.mobitribe.com.app_time_recorder.bals.ActivityLogBAL;
import app.atr.mobitribe.com.app_time_recorder.extras.SharedPreferences;
import app.atr.mobitribe.com.app_time_recorder.interfaces.ResponseCallBack;
import app.atr.mobitribe.com.app_time_recorder.model.ApplicationLog;
import app.atr.mobitribe.com.app_time_recorder.model.CustomUsageStats;
import app.atr.mobitribe.com.app_time_recorder.model.Response;
import app.atr.mobitribe.com.app_time_recorder.network.RestClient;
import app.atr.mobitribe.com.app_time_recorder.utills.ActivityLogManager;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Author: Uzair Qureshi
 * Date:  7/5/17.
 * Description:
 */

public class ActivtiyLogJob extends Job {
    public static final String TAG = "job_demo_tag";
    public SharedPreferences sharedPreferences;
    UsageStatsManager mUsageStatsManager;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat();

    public static void scheduleJob() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis()+(2*1000));
////        calendar.set(Calendar.HOUR_OF_DAY, 12);
////        calendar.set(Calendar.MINUTE, 22);
        new JobRequest.Builder(ActivtiyLogJob.TAG)

               .setPeriodic(TimeUnit.MINUTES.toMillis(1))
                //.setExact(2*1000)
                .setUpdateCurrent(true)//this request will cancel any preexisting job with the same tag while being scheduled.?true :false
                .setPersisted(true)
                .build()
                .schedule();
    }

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        sharedPreferences = new SharedPreferences(this.getContext());
        ActivityLogManager logManager = new ActivityLogManager(getContext());
        ApplicationLog applicationLog = new ApplicationLog();
        applicationLog.setApplications(logManager.getAppsList());
        ActivityLogBAL.postActivityLog(getContext(), applicationLog, new ResponseCallBack() {
            @Override
            public void onSuccess() {
                Notification notification = new NotificationCompat.Builder(getContext())
                        .setContentTitle("Android Job Done")
                        .setContentText("Server request done.")
                        .setAutoCancel(true)
                        //.setContentIntent(pi)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setShowWhen(true)
                        .setColor(Color.RED)
                        .setLocalOnly(true)
                        .build();

                NotificationManagerCompat.from(getContext())
                        .notify(new Random().nextInt(), notification);


            }

            @Override
            public void onofflineSaveData(ApplicationLog applicationLog) {
                sharedPreferences.saveActivityLog(applicationLog);


            }


        });

        return Result.SUCCESS;


    }

    @Override
    protected void onReschedule(int newJobId) {
        super.onReschedule(newJobId);
        Log.i("Reschedule", "run task");


    }


    private void pushAppLogsToServer(List<ApplicationLog.Application> applications) {

        ApplicationLog applicationLog = new ApplicationLog();
        applicationLog.setApplications(applications);
        if (isOnline()) {
            RestClient.getAuthRestAdapter().
                    postActivityLogs(applicationLog).
                    enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            if (response.isSuccessful() && response.body() != null && response.body().getMeta().getSuccess()) {
                                Log.println(Log.VERBOSE, TAG, "Run My JOb");
                                Notification notification = new NotificationCompat.Builder(getContext())
                                        .setContentTitle("Android Job Done")
                                        .setContentText("Server request done.")
                                        .setAutoCancel(true)
                                        //.setContentIntent(pi)
                                        .setSmallIcon(R.drawable.ic_launcher)
                                        .setShowWhen(true)
                                        .setColor(Color.RED)
                                        .setLocalOnly(true)
                                        .build();

                                NotificationManagerCompat.from(getContext())
                                        .notify(new Random().nextInt(), notification);

                            } else {
                                Log.i(TAG, "some error found using retrofit");
                                // isSuccessful[0] =false;
                            }
                            // ((ParentActivity)getActivity()).showMessage(getView(),"Logs successfully updated!");
                        }

                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {
                            // ((ParentActivity)getActivity()).onFailureResponse(getView(),t);
                            Log.i(TAG, "some error found using retrofit");
                            // isSuccessful[0] =false;

                        }
                    });
        } else {
            //saved in sharedprefernce
            sharedPreferences.saveActivityLog(applicationLog);
        }

    }




    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}
