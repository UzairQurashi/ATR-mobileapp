package app.atr.mobitribe.com.app_time_recorder.extras;

import android.content.Context;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import app.atr.mobitribe.com.app_time_recorder.model.profile.User;

/**
 * Author: Muhammad Shahab
 * Date: 5/9/17.
 * Description: class to save or retrieve data from shared preferences
 */

public class SharedPreferences {

    private static final String USER = "USER";
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

}
