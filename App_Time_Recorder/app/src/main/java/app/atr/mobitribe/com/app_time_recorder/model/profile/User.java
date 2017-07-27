package app.atr.mobitribe.com.app_time_recorder.model.profile;

/**
 * Author: Muhammad Shahab
 * Date: 5/24/17.
 * Description: User profile class that use to cache necessary data
 */

public class User {
    private String access_token;
    private String token_type;
    private static User jobSeeker;

    public static synchronized User getInstance(){

        if(jobSeeker == null){

            jobSeeker = new User();
        }

        return jobSeeker;

    }
    public static synchronized User getInstance(User mJobSeeker){

        if(mJobSeeker == null){

            return getInstance();
        }

        return jobSeeker = mJobSeeker;

    }
    public static synchronized void resetInstance(){
        jobSeeker = new User();
    }

    public String getAccess_token() {
        return access_token;
    }

    public User setAccess_token(String access_token) {
        this.access_token = access_token;
        return this;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }
}
