package app.atr.mobitribe.com.app_time_recorder.bals;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import app.atr.mobitribe.com.app_time_recorder.interfaces.ResponseCallBack;
import app.atr.mobitribe.com.app_time_recorder.model.ApplicationLog;
import app.atr.mobitribe.com.app_time_recorder.model.Response;
import app.atr.mobitribe.com.app_time_recorder.network.RestClient;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Author: Uzair Qureshi
 * Date:  7/27/17.
 * Description:
 */

public class ActivityLogBAL {

    public static void postActivityLog(Context context, final ApplicationLog applicationLog, final ResponseCallBack callBack) {

//        if (isNetworkAvailable(context)) {
            RestClient.getAuthRestAdapter().postActivityLogs(applicationLog).enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callBack.onSuccess();

                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                   // callBack.onFailure();
                    callBack.onofflineSaveData(applicationLog);

                }
            });
//        } else {
//            callBack.onofflineSaveData(applicationLog);
//        }

    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
