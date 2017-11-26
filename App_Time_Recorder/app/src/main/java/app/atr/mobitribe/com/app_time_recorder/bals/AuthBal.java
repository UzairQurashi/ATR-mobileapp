package app.atr.mobitribe.com.app_time_recorder.bals;

import android.content.Context;

import app.atr.mobitribe.com.app_time_recorder.interfaces.GeneralCallBack;
import app.atr.mobitribe.com.app_time_recorder.interfaces.GeneralResponse;
import app.atr.mobitribe.com.app_time_recorder.interfaces.ResponseCallBack;
import app.atr.mobitribe.com.app_time_recorder.model.responses.Companies;
import app.atr.mobitribe.com.app_time_recorder.network.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author: Uzair Qureshi
 * Date:  8/28/17.
 * Description:
 */

public class AuthBal extends GeneralCallBack {
    /**
     * @param context
     * @purpose Parameter constructor
     */
    public AuthBal(Context context) {
        super(context);
    }

    @Override
    public void onSuccess(Response response) {

    }
//    /**
//     * this method will fetch all  sub-admins credianls
//     * @param callBack
//     */
//    public static void getAllCompanies(final Context context, final GeneralResponse<Companies> callBack){
//        RestClient.getAuthClientAdapter().getAllCompanies().enqueue(new Callback<Companies>() {
//            @Override
//            public void onResponse(Call<Companies> call, Response<Companies> response) {
//                if(response.isSuccessful()&&response.body()!=null){
//                    callBack.onSuccess(response.body());
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<Companies> call, Throwable t) {
//                callBack.onFailure(t,context);
//            }
//        });
//
//
//    }
}
