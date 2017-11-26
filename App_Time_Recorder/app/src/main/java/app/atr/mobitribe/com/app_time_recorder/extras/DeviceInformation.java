package app.atr.mobitribe.com.app_time_recorder.extras;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * Author: Uzair Qureshi
 * Date:  10/30/17.
 * Description:
 */

public class DeviceInformation {


    public static String getDeviceModel() {
        String devie_model = String.valueOf(Build.MODEL);
        return devie_model;
    }

    public static String getImei(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public static String getNetworkOperatorName(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String cellular_name = telephonyManager.getNetworkOperatorName();
        if (cellular_name.equals("")) {
            cellular_name = "Cellular network not found";
        }
        return cellular_name;
    }
}
