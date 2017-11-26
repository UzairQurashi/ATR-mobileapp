package app.atr.mobitribe.com.app_time_recorder.screens;

import android.Manifest;
import android.app.AlertDialog;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import app.atr.mobitribe.com.app_time_recorder.R;
import app.atr.mobitribe.com.app_time_recorder.bals.AuthBal;
import app.atr.mobitribe.com.app_time_recorder.interfaces.GeneralCallBack;
import app.atr.mobitribe.com.app_time_recorder.interfaces.GeneralResponse;
import app.atr.mobitribe.com.app_time_recorder.model.responses.Companies;
import app.atr.mobitribe.com.app_time_recorder.network.RestClient;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Response;


public class SplashScreen extends ParentActivity implements EasyPermissions.PermissionCallbacks {

    private static final int REQUEST_LOCATION_CODE = 500;
    private static final int REQUEST_ENABLE = 123;
    DevicePolicyManager mDPM;
    ComponentName mAdminName;
    private long SPLASH_TIME_OUT = 3000;

    public SplashScreen() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        mAdminName = new ComponentName(this, MyDeviceAdministrator.class);
        checkDeviceAdmin();

    }



//============================================ Helper Methods =======================================================//
    /**
     * this method will navigate to Login screen
     */
    private void openNewScreen() {
        new Handler().postDelayed(new Runnable() {
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(i);
                finish();

            }
        }, SPLASH_TIME_OUT);


    }

    /**
     * this method will check location runtime permission
     */
    @AfterPermissionGranted(REQUEST_LOCATION_CODE)
    private void checkLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.READ_PHONE_STATE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            //showDialogue();

                openNewScreen();


        } else {
            //ask for permission
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_location),
                    REQUEST_LOCATION_CODE, perms);


        }


    }

    /**
     * this method will check whether device admin is active or not and do appropriate operations
     */
    private void checkDeviceAdmin() {
        if (!mDPM.isAdminActive(mAdminName)) {
            // try to become active – must happen here in this activity, to get result
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                    mAdminName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    "Additional text explaining why this needs to be added");
            startActivityForResult(intent, REQUEST_ENABLE);
        } else {
            // Already is a device administrator, can do security operations now.
            //mDPM.lockNow();
            checkLocationPermission();
        }

    }





    //============================================= callbacks ==========================================================//


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //openNewScreen();

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d("Location", "onPermissionsDenied:" + requestCode + ":" + perms.size());


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_ENABLE == requestCode) {
            if (resultCode == SampleActivty.RESULT_OK) {
                checkLocationPermission();
                // Has become the device administrator.


            } else {
                //Canceled or failed.

            }
        }
    }
    //================================================= Inner Class ======================================================//

    /**
     * this inner class is Device Admin Receiver class
     */
    public static class MyDeviceAdministrator extends DeviceAdminReceiver {


        private void showToast(Context context, CharSequence msg) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }


        @Override
        public void onEnabled(Context context, Intent intent) {
            showToast(context, "Sample Device Admin: enabled");
        }


        @Override
        public void onDisabled(Context context, Intent intent) {
            showToast(context, "Sample Device Admin: disabled");
            // Log.e("DA-Disabled","Sample Device Admin: disabled");

        }


    }

}
