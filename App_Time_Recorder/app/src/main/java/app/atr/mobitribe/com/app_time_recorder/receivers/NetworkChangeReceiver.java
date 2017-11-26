package app.atr.mobitribe.com.app_time_recorder.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import app.atr.mobitribe.com.app_time_recorder.bals.ActivityLogBAL;
import app.atr.mobitribe.com.app_time_recorder.extras.SharedPreferences;
import app.atr.mobitribe.com.app_time_recorder.interfaces.ResponseCallBack;
import app.atr.mobitribe.com.app_time_recorder.model.ApplicationLog;
import app.atr.mobitribe.com.app_time_recorder.network.RestClient;

/**
 * Author: Uzair Qureshi
 * Date:  7/27/17.
 * Description:
 */

public class NetworkChangeReceiver extends BroadcastReceiver {
    SharedPreferences sharedPreferences;



    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPreferences=new SharedPreferences(context);


        if(isNetworkAvailable(context)){

            Log.d("Network Available ", "Flag No 1");
            //Toast.makeText(context,"Do your work",Toast.LENGTH_SHORT).show();
            if(sharedPreferences.getApplicationLog()!=null)
            {
                pushToServer(context);
            }

        }
    }
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
    private void pushToServer(final Context context){
        ActivityLogBAL.postActivityLog(context, sharedPreferences.getApplicationLog(), new ResponseCallBack() {
            @Override
            public void onSuccess() {
                Toast.makeText(context,"oofline data saved successfully ",Toast.LENGTH_SHORT).show();
                sharedPreferences.clearApplicationLog();//clear cache

            }

            @Override
            public void onofflineSaveData(ApplicationLog applicationLog) {

            }
        });
    }
}
