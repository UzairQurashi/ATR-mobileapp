package app.atr.mobitribe.com.app_time_recorder.screens;

import android.databinding.DataBindingUtil;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.atr.mobitribe.com.app_time_recorder.R;
import app.atr.mobitribe.com.app_time_recorder.databinding.ActivityDeviceInfoActivtiyBinding;

public class DeviceInfoActivtiy extends AppCompatActivity {
    ActivityDeviceInfoActivtiyBinding binding;
    private String model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_device_info_activtiy);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_device_info_activtiy);
        model= String.valueOf(Build.VERSION.SDK_INT);
        binding.deviceInfo.setText(""+model.toString());

    }
}
