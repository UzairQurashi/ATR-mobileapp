package app.atr.mobitribe.com.app_time_recorder.model;

import java.util.List;

/**
 * Author: Muhammad Shahab
 * Date: 6/12/17.
 * Description:
 */


public class ApplicationLog {

    private String time_interval;
    private List<Application> applications = null;

    public ApplicationLog() {
        this.time_interval = "daily";
    }

    public String getTime_interval() {
        return time_interval;
    }

    public void setTime_interval(String time_interval) {
        this.time_interval = time_interval;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }


    public class Application {

        private String package_name;
        private String last_time_used;
        private String foreground_time;

        public String getPackage_name() {
            return package_name;
        }

        public void setPackage_name(String package_name) {
            this.package_name = package_name;
        }

        public String getLast_time_used() {
            return last_time_used;
        }

        public void setLast_time_used(String last_time_used) {
            this.last_time_used = last_time_used;
        }

        public String getForeground_time() {
            return foreground_time;
        }

        public void setForeground_time(String foreground_time) {
            this.foreground_time = foreground_time;
        }

    }

}



