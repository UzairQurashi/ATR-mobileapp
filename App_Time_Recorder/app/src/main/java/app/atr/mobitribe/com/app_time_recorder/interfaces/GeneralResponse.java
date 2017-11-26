package app.atr.mobitribe.com.app_time_recorder.interfaces;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import java.net.ConnectException;

/**
 * Author: Uzair Qureshi
 * Date:  8/28/17.
 * Description:
 */

public abstract class GeneralResponse<T> {
  public   abstract void onSuccess(T response);

    public void onFailure(Throwable throwable, Context context) {
        if (throwable instanceof ConnectException) {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Something goes wrong!", Toast.LENGTH_SHORT).show();


        }


    }


}
