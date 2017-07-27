package app.atr.mobitribe.com.app_time_recorder.jobscheduler;

import android.app.Notification;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.graphics.Color;
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
import app.atr.mobitribe.com.app_time_recorder.model.ApplicationLog;
import app.atr.mobitribe.com.app_time_recorder.model.CustomUsageStats;
import app.atr.mobitribe.com.app_time_recorder.model.Response;
import app.atr.mobitribe.com.app_time_recorder.network.RestClient;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Author: Uzair Qureshi
 * Date:  7/5/17.
 * Description:
 */

public class ActivtiyLogJob extends Job {
    public static final String TAG = "job_demo_tag";
    UsageStatsManager mUsageStatsManager;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat();




    @NonNull
    @Override
    protected Result onRunJob(Params params)
    {

        mUsageStatsManager = (UsageStatsManager) getContext()
                .getSystemService(Context.USAGE_STATS_SERVICE);
        updateAppsList(getUsageStatistics(UsageStatsManager.INTERVAL_DAILY));
       // Log.e(TAG,"runjob");
        return Result.SUCCESS;
    }

    @Override
    protected void onReschedule(int newJobId) {
        super.onReschedule(newJobId);
       // Toast.makeText(getContext(),"Re-Run My JOb",Toast.LENGTH_LONG).show();


    }

    public static void scheduleJob() {
        new JobRequest.Builder(ActivtiyLogJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(1))
                .setUpdateCurrent(true)//this request will cancel any preexisting job with the same tag while being scheduled.?true :false
                .setPersisted(true)
                .build()
                .schedule();
    }
    private void pushAppLogsToServer(List<ApplicationLog.Application> applications) {

        ApplicationLog applicationLog = new ApplicationLog();
        applicationLog.setApplications(applications);
        RestClient.getAuthRestAdapter().
                postActivityLogs(applicationLog).
                enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (response.isSuccessful()&&response.body()!=null&&response.body().getMeta().getSuccess())
                        {
                            Log.println(Log.VERBOSE,TAG,"Run My JOb");
//                            Intent intent=new Intent(getContext(), DeviceInfoActivtiy.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//                            getContext().startActivity(intent);

//                            PendingIntent pi = PendingIntent.getActivity(getContext(), 0,
//                                    new Intent(getContext(), DeviceInfoActivtiy.class), 0);
//                            PendingIntent pi = PendingIntent.getActivity(getContext(), 0,
//                                  intent, 0);

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
                        else {
                            Log.i(TAG, "some error found using retrofit");
                        }
                           // ((ParentActivity)getActivity()).showMessage(getView(),"Logs successfully updated!");
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                       // ((ParentActivity)getActivity()).onFailureResponse(getView(),t);
                        Log.i(TAG, "some error found using retrofit");

                    }
                });
    }
    public List<UsageStats> getUsageStatistics(int intervalType) {
        // Get the app statistics since one year ago from the current time.
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);

        List<UsageStats> queryUsageStats = mUsageStatsManager
                .queryUsageStats(intervalType, cal.getTimeInMillis(),
                        System.currentTimeMillis());

        if (queryUsageStats.size() == 0) {
            Log.i(TAG, "The user may not allow the access to apps usage. ");
            //getContext().startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));



        }
        return queryUsageStats;
    }
    void updateAppsList(List<UsageStats> usageStatsList) {

        List<ApplicationLog.Application> applications = new ArrayList<>();
        //List<CustomUsageStats> customUsageStatsList = new ArrayList<>();
        for (int i = 0; i < usageStatsList.size(); i++) {
            CustomUsageStats customUsageStats = new CustomUsageStats();
            ApplicationLog.Application application = new ApplicationLog().new Application();

            customUsageStats.usageStats = usageStatsList.get(i);
            application.setLast_time_used(String.valueOf(customUsageStats.usageStats.getLastTimeUsed()));
            application.setForeground_time(String.valueOf(customUsageStats.usageStats.getTotalTimeInForeground()));
            application.setPackage_name(customUsageStats.usageStats.getPackageName());
            applications.add(application);

        }
        pushAppLogsToServer(applications);
    }

}
