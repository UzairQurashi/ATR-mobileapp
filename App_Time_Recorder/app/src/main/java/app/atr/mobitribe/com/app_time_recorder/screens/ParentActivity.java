package app.atr.mobitribe.com.app_time_recorder.screens;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import java.io.IOException;

import app.atr.mobitribe.com.app_time_recorder.R;
import app.atr.mobitribe.com.app_time_recorder.extras.ProgressLoader;
import app.atr.mobitribe.com.app_time_recorder.extras.SharedPreferences;

import static android.provider.Settings.Global.AIRPLANE_MODE_ON;

/**
 * Author: Muhammad Shahab
 * Date: 5/2/17.
 * Description:It is the parent class of all activities
 */

public class ParentActivity extends AppCompatActivity {

    private static final String TAG = "ParentActivity";
    public SharedPreferences sharedPreferences;
    private ProgressLoader progressLoader;

    /**
     * @usage onCreate method that will be called by all child class instances to initialize some useful objects
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = new SharedPreferences(this);
    }

    /**
     * @usage it opens the activity receives in parameter
     * @param activity
     */
    public void openActivity(Class activity)
    {
        startActivity(new Intent(this,activity));
    }

    /**
     * @usage it opens the activity receives in parameter and finish  the current activity running
     * @param activity
     */
    public void openActivityWithFinish(Class activity)
    {
        startActivity(new Intent(this,activity));
        finish();
    }

    /**
     * @usage it opens the activity with provide intent and finish  the current activity running
     * @param intent
     */
    public void openActivityWithFinish(Intent intent)
    {
        startActivity(intent);
        finish();
    }

    /**
     * @usage It opens the activity with the provided bundle as a parameter
     * @param activity
     * @param bundle
     */
    public void openActivity(Class activity, Bundle bundle)
    {
        startActivity(new Intent(this,activity).putExtras(bundle));
    }

    /**
     * @usage It opens the activity for result with the provided bundle as a parameter
     * @param activity
     * @param bundle
     */
    public void openActivityForResults(Class activity, Bundle bundle, int requestCode)
    {
        startActivityForResult(new Intent(this,activity).putExtras(bundle), requestCode);
    }


    /**
     * @usage It opens the activity for result
     * @param activity
     */
    public void openActivityForResults(Class activity, int requestCode)
    {
        startActivityForResult(new Intent(this,activity), requestCode);
    }


    /**
     * @usage Making notification bar transparent
     */
    public void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * @usage it hides the status bar
     */
    public void hideStatusBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    /**
     * @usage It use to show any message provided by the caller
     * @param view
     * @param message
     */
    public void showMessage(View view, String message)
    {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * @usage it handles onFailure Response of retrofit
     * @param throwable
     * @param view
     */
    public void onFailureResponse(View view,Throwable throwable)
    {
        if (throwable instanceof IOException)
        {
            showMessage(view,getString(R.string.internet_connectivity));
        }
        else
        {
            showMessage(view,getString(R.string.some_thing_went_wrong));
        }
    }

    /**
     * @usage It shows the progress loader when it gets called
     */
    public void showProgress()
    {
        try {
            if (progressLoader == null)
            {
                progressLoader = new ProgressLoader();
            }

            progressLoader.show(getSupportFragmentManager(),TAG);
        }
        catch (IllegalStateException e)
        {
            Log.e(TAG, "showProgress:" + e.getMessage());
        }

    }

    /**
     * @usage It hides the progress loader when it gets called
     */
    public void hideProgress() {
        if (progressLoader != null) {
            try {
                progressLoader.dismissAllowingStateLoss();
            } catch (Exception e) {
                Log.e(TAG, "hideProgress:" + e.getMessage());
            }
        }
    }

    /**
     * @usage onBackPressed for the onClick in xml layout
     * @param v
     */
    public void onBackPressed(View v)
    {
        super.onBackPressed();
    }

    protected boolean isAirplaneModeOn() {
        ContentResolver contentResolver = getContentResolver();
        return Settings.System.getInt(contentResolver, AIRPLANE_MODE_ON, 0) != 0;
    }


    /**
     * @usage finish the activity after setting result for calling activity
     * @param data
     */
    public void finishWithResult(Intent data)
    {
       setResult(RESULT_OK,data);
       finish();
    }

}
