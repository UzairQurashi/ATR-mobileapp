package app.atr.mobitribe.com.app_time_recorder.jobscheduler;

import android.content.Context;
import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;
import com.evernote.android.job.JobManager;

/**
 * Author: Uzair Qureshi
 * Date:  7/5/17.
 * Description:
 */

public class JobFactory implements JobCreator {
    @Override
    public Job create(String tag) {
        switch (tag) {
            case ActivtiyLogJob.TAG:
                return new ActivtiyLogJob();
            case LocationJob.TAG:
               return new LocationJob();
            default:
                return null;
        }
    }
    public static final class AddReceiver extends AddJobCreatorReceiver {
        @Override
        protected void addJobCreator(@NonNull Context context, @NonNull JobManager manager) {
            // manager.addJobCreator(new DemoJobCreator());
        }
    }
}
