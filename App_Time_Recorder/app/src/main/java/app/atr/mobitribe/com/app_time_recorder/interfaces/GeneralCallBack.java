package app.atr.mobitribe.com.app_time_recorder.interfaces;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.net.ConnectException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Author: Muhammad Shahab.
 * Organization: Mobitribe
 * Date: 8/28/17
 * Description: General Callback for all api calls
 */

public abstract class GeneralCallBack<T> implements Callback<T> {

    private final Context context;



    abstract public void onSuccess(Response<T> response);


    /**
     * @purpose Parameter constructor
     * @param context
     */
    public GeneralCallBack(Context context)
    {
        this.context = context;
    }

    /*this method will invoked when server gives an reponse and this have multiple implementation according to scenario*/
    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        if (response.isSuccessful() && response.body() != null) {
            onSuccess(response);
        } else {
            onServerFailure("Something goes wrong!");
        }
     //   if (response.body() instanceof com.mobitribe.temprovide.model.response.Response)
//        {
//            com.mobitribe.temprovide.model.response.Response serverResponse = (com.mobitribe.temprovide.model.response.Response)response.body();
//            if (response.isSuccessful()&&response.body()!=null&&serverResponse.getMeta().getSuccess())
//                onSuccess(response);
//            else
//                onServerFailure(serverResponse);
//        }


    }


    @Override
    public void onFailure(Call<T> call, Throwable throwable) {
        if (throwable instanceof IOException) {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Something goes wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @purpose When server fails due to some validations
     * @param message
     */
    private void onServerFailure( String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
