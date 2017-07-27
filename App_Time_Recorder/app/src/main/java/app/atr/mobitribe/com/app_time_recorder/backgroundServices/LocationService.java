package app.atr.mobitribe.com.app_time_recorder.backgroundServices;

import android.Manifest;
import android.app.IntentService;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.HashMap;

import app.atr.mobitribe.com.app_time_recorder.model.Response;
import app.atr.mobitribe.com.app_time_recorder.model.UserLocation;
import app.atr.mobitribe.com.app_time_recorder.model.profile.User;
import app.atr.mobitribe.com.app_time_recorder.network.RestClient;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by uzair on 7/26/16.
 */
public class LocationService extends IntentService
        implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    //private LocationRequest mLocationRequest;
    public static final String TAG = "LocationService";
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;
    //private LocationClient locationClient;
//    FirebaseDatabase mRootDB=FirebaseDatabase.getInstance();
//    DatabaseReference mdbRef=mRootDB.getReference("AgentsLocation");
    private float resLocation=0;
    double currentLatitude =0;
    double currentLongitude=0;

    public LocationService() {
        super("LocationService");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        buildGoogleClient();
        setLocationRequest();
        //mGoogleApiClient.connect();
       // DevicePreference.getInstance().init(getApplicationContext());
        Toast.makeText(getApplicationContext(), "Monitoring Start", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("Service test", "On handle intent method");
//        if(mGoogleApiClient.isConnected()) {
//            handleNewLocation(mLastLocation);
//        }
//        if(!mGoogleApiClient.isConnected() || !mGoogleApiClient.isConnecting()) {
//            mGoogleApiClient.connect();
//        }


    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(mGoogleApiClient.isConnected())
            // handleNewLocation(mlocation);
            return START_STICKY;

        if(!mGoogleApiClient.isConnected() || !mGoogleApiClient.isConnecting()) {
            mGoogleApiClient.connect();
        }

        return START_STICKY;
       // return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        setLocationRequest();


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,this);
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if(mLastLocation!=null){

            handleNewLocation(mLastLocation);
        }



    }

    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());


        if (location != null) {
            sendLocationToServer(location);//send location data to server
        }


    }


    private void getDistance(Location location){
        float[] results = new float[3];

        if(currentLatitude!=0&&currentLongitude!=0)
        {
            Location.distanceBetween(currentLatitude, currentLongitude, location.getLatitude(), location.getLongitude(), results);
            resLocation+=results[0];
            Toast.makeText(getApplicationContext(), "Distance" + resLocation, Toast.LENGTH_SHORT).show();
        }
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();

    }






    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution())
        {
            try {
                // Start an Activity that tries to resolve the error
                Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());

                connectionResult.startResolutionForResult(null, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }

        else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }



    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation=location;
       // Toast.makeText(this,"On location Changed"+mLastLocation.getLat()+mLastLocation.getLng(),Toast.LENGTH_LONG).show();
        handleNewLocation(mLastLocation);


    }

    /**
     * this method will location data to server
     * @param location
     */
    private void sendLocationToServer(Location location){
        UserLocation userLocation=new UserLocation(String.valueOf(location.getLatitude()),(String.valueOf(location.getLongitude())));
        RestClient.getAuthRestAdapter().postUserLocation(userLocation).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.isSuccessful()&&response.body()!=null)
                {
                   // Log.d(TAG,response.body().getMeta().getMessage());

                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
             //   Log.d(TAG,t.getMessage());

            }
        });

    }






    @Override
    public void onDestroy() {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
            mGoogleApiClient.disconnect();
            Toast.makeText(this,"Monitoring Stop", Toast.LENGTH_SHORT).show();
        }
        super.onDestroy();
    }

    private void buildGoogleClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();



    }
    private void setLocationRequest()
    {
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
               .setInterval(10* 1000)// 5 seconds, in milliseconds
              //.setSmallestDisplacement(10);
                .setFastestInterval(5 * 1000); // 60 second, in milliseconds


    }


}
