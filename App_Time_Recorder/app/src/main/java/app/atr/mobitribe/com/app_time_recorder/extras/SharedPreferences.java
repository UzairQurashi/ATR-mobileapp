package app.atr.mobitribe.com.app_time_recorder.extras;

import android.content.Context;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import app.atr.mobitribe.com.app_time_recorder.model.ApplicationLog;
import app.atr.mobitribe.com.app_time_recorder.model.profile.User;
import app.atr.mobitribe.com.app_time_recorder.model.responses.Companies;

/**
 * Author: Muhammad Shahab
 * Date: 5/9/17.
 * Description: class to save or retrieve data from shared preferences
 */

public class SharedPreferences {

    private static final String USER = "USER";
    private static final String ACTIVITY="ACTIVITY_LOG";
    private static final String COMPANIES="Comapnies";
    private final android.content.SharedPreferences mPrefs;

    public SharedPreferences(Context mContext) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    
    public boolean saveUser() {
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(User.getInstance());
        prefsEditor.putString(USER, json);
        return prefsEditor.commit();
    }

    public User getUser() {
        Gson gson = new Gson();
        String json = mPrefs.getString(USER, "");
        Type type = new TypeToken<User>() {}.getType();
        return gson.fromJson(json,type);
    }
    public void saveActivityLog(ApplicationLog applicationLog){
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(applicationLog);
        prefsEditor.putString(ACTIVITY, json);
        prefsEditor.commit();

    }
    public ApplicationLog getApplicationLog(){
        Gson gson = new Gson();
        String json = mPrefs.getString(ACTIVITY, "");
        Type type = new TypeToken<ApplicationLog>() {}.getType();
        return gson.fromJson(json,type);

    }
    public void clearApplicationLog(){
        if(getApplicationLog()!=null){
            android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
            prefsEditor.remove(ACTIVITY);
            prefsEditor.commit();


        }

    }

    public void saveCompanies(Companies companies) {
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(companies);
        prefsEditor.putString(COMPANIES, json);
        prefsEditor.commit();

    }
    public Companies getCompanies(){
        Gson gson = new Gson();
        String json = mPrefs.getString(COMPANIES, "");
        Type type = new TypeToken<Companies>() {}.getType();
        return gson.fromJson(json,type);

    }
}
