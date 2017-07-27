package app.atr.mobitribe.com.app_time_recorder.jobscheduler;

import android.app.Application;
import android.content.IntentFilter;

import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobManagerCreateException;


/**
 * Author: Uzair Qureshi
 * Date:  7/5/17.
 * Description:
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            JobManager.create(this).addJobCreator(new JobFactory());
            JobManager.instance().getConfig().setAllowSmallerIntervalsForMarshmallow(true); // Don't use this in production

        } catch (JobManagerCreateException e) {
            e.printStackTrace();
        }
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("android.intent.action.QUERY_PACKAGE_RESTART");
//       // android.intent.action.QUERY_PACKAGE_RESTART
        //registerReceiver(new UnInstallerReceiver(),filter);
    }

}