package app.atr.mobitribe.com.app_time_recorder.network;




import java.util.HashMap;

import app.atr.mobitribe.com.app_time_recorder.model.ApplicationLog;
import app.atr.mobitribe.com.app_time_recorder.model.LoginResponse;
import app.atr.mobitribe.com.app_time_recorder.model.Register;
import app.atr.mobitribe.com.app_time_recorder.model.Response;
import app.atr.mobitribe.com.app_time_recorder.model.UserLocation;
import app.atr.mobitribe.com.app_time_recorder.model.responses.Companies;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Author: Muhammad Shahab
 * Date: 5/5/17.
 * Description: Interface that contains the services
 */

public interface WebServices {

    @FormUrlEncoded
    /*Employer login*/
    @POST(ApiEndPoints.LOGIN)
    Call<LoginResponse> loginUser(@FieldMap HashMap<String, String> login);


    /*Employer login*/
    @POST(ApiEndPoints.REGISTER)
    Call<LoginResponse> registration(@Body Register login);

    @POST(ApiEndPoints.ACTIVITY_LOGS)
    Call<Response> postActivityLogs(@Body ApplicationLog applicationLog);
    /*EMployer Location tracking*/
    @POST(ApiEndPoints.LOCATION_LOGS)
    Call<Response> postUserLocation(@Body UserLocation userLocation);
    /*Get sub admins*/
    @GET(ApiEndPoints.GET_ALL_COMPANIES)
    Call<Companies> getAllCompanies();



}
