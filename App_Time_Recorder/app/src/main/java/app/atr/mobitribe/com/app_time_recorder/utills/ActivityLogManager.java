package app.atr.mobitribe.com.app_time_recorder.utills;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import app.atr.mobitribe.com.app_time_recorder.model.ApplicationLog;
import app.atr.mobitribe.com.app_time_recorder.model.CustomUsageStats;

/**
 * Author: Uzair Qureshi
 * Date:  7/27/17.
 * Description:
 */

public class ActivityLogManager {
    private Context context;
    private static String TAG="ActivityLogManagerLog";


    public ActivityLogManager(Context context) {
        this.context = context;
    }
    /*
    this method will create  usagestatsManager instance
     */
    public UsageStatsManager usageStatsManagerBuilder(){
       return  (UsageStatsManager) context
                .getSystemService(Context.USAGE_STATS_SERVICE);
    }
    /*
      this method will give list of usagestats data with given interval of time
     */
    public List<UsageStats> getUsageStatistics(int intervalType) {
        // Get the app statistics since one year ago from the current time.
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);

        List<UsageStats> queryUsageStats = usageStatsManagerBuilder()
                .queryUsageStats(intervalType, cal.getTimeInMillis(),
                        System.currentTimeMillis());

        if (queryUsageStats.size() == 0) {
            Log.i(TAG, "The user may not allow the access to apps usage. ");
            //getContext().startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));



        }
        return queryUsageStats;
    }
  public List<ApplicationLog.Application> getAppsList() {
      List<UsageStats> usageStatsList=getUsageStatistics(UsageStatsManager.INTERVAL_DAILY);

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
       // pushAppLogsToServer(applications);
      return applications;
    }

}
