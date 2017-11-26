package app.atr.mobitribe.com.app_time_recorder.screens;

import android.app.AlertDialog;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import app.atr.mobitribe.com.app_time_recorder.R;
import app.atr.mobitribe.com.app_time_recorder.adapters.CustomSpinnerAdapter;
import app.atr.mobitribe.com.app_time_recorder.backgroundServices.LocationService;
import app.atr.mobitribe.com.app_time_recorder.databinding.ActivityLoginBinding;
import app.atr.mobitribe.com.app_time_recorder.extras.DeviceInformation;
import app.atr.mobitribe.com.app_time_recorder.extras.ValidationUtility;
import app.atr.mobitribe.com.app_time_recorder.jobscheduler.ActivtiyLogJob;
import app.atr.mobitribe.com.app_time_recorder.jobscheduler.LocationJob;
import app.atr.mobitribe.com.app_time_recorder.model.Login;
import app.atr.mobitribe.com.app_time_recorder.model.LoginResponse;
import app.atr.mobitribe.com.app_time_recorder.model.Register;
import app.atr.mobitribe.com.app_time_recorder.model.profile.User;
import app.atr.mobitribe.com.app_time_recorder.model.responses.Companies;
import app.atr.mobitribe.com.app_time_recorder.network.NetworkContants;
import app.atr.mobitribe.com.app_time_recorder.network.RestClient;
import pub.devrel.easypermissions.AfterPermissionGranted;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends ParentActivity {

    private static final int REQUEST_LOCATION_CODE = 500;
   private Companies.Subadmin subadmin ;
   private int count=0;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private boolean login = true;

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        if (!doIHavePermission()) {
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        }
        setListeners();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isGPSEnabled(this)){
            showSettingsAlert();
        }
    }

    private void setListeners() {
        binding.loginForm.password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        binding.regisFormTwo.regConfirmPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptSignUp();
                    return true;
                }
                return false;
            }
        });

        binding.regLoginToggle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //loadSpinner();
                if (login) {
                    login = false;
                    binding.registrationForm.setVisibility(View.VISIBLE);
                    binding.loginForm.loginForm.setVisibility(View.INVISIBLE);
                    binding.regLoginToggle.setText(R.string.login);
                } else {
                    login = true;
                    binding.loginForm.loginForm.setVisibility(View.VISIBLE);
                    binding.registrationForm.setVisibility(View.INVISIBLE);
                    binding.regLoginToggle.setText(R.string.registration);
                }
            }
        });

        binding.loginForm.signIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        binding.regisFormTwo.reg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSignUp();
            }
        });

        binding.regisFormOne.next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validatePhaseOne()) {
                    binding.regisFormOne.regFirstPhase.setVisibility(View.INVISIBLE);
                    binding.regisFormTwo.regSecondPhase.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.regisFormTwo.up.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.regisFormOne.regFirstPhase.setVisibility(View.VISIBLE);
                binding.regisFormTwo.regSecondPhase.setVisibility(View.INVISIBLE);
            }
        });
    }



    private void attemptSignUp() {


        if (validatePhaseTwo()) {
            showProgress();
            final Register register = new Register();
            register.setAddress(binding.regisFormOne.Address.getText().toString());
            register.setCity(binding.regisFormOne.city.getText().toString());
            register.setCnic(binding.regisFormOne.cnic.getText().toString());
            register.setFirst_name(binding.regisFormOne.firstName.getText().toString());
            register.setLast_name(binding.regisFormOne.lastName.getText().toString());
            register.setPhone(binding.regisFormOne.mobileNumber.getText().toString());
            register.setUsername(binding.regisFormTwo.regEmail.getText().toString());
            register.setPassword(binding.regisFormTwo.regPassword.getText().toString());
            register.setRetype_password(binding.regisFormTwo.regConfirmPass.getText().toString());
            register.setGender(binding.regisFormOne.gender.getCheckedRadioButtonId() == R.id.male ? "male" : "female");
            register.setSecurity_keywords(binding.regisFormTwo.securityKeyword.getText().toString());
            register.setAndroid_version(getDeviceVersion());
            register.setCellular_network(DeviceInformation.getNetworkOperatorName(this));
            register.setDevice_model(DeviceInformation.getDeviceModel());
            //register.setSub_admin_id(String.valueOf(subadmin.getId()));
            register.setImei_numer(DeviceInformation.getImei(this));
            RestClient.getAuthRestAdapter().registration(register).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                    hideProgress();
                    if (response.isSuccessful() && response.body() != null && response.body().getMeta().getSuccess()) {
                        User.getInstance().setAccess_token(response.body().getAccess_token());
                        sharedPreferences.saveUser();
                        showMessage(binding.getRoot(),"Now You can Login !");
                    } else if (response.body() != null) {
                        showMessage(binding.getRoot(), response.body().getMeta().getMessage_detail());
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    hideProgress();
                    onFailureResponse(binding.getRoot(), t);
                }
            });
        }
    }

    private boolean validatePhaseOne() {
        if (!ValidationUtility.edittextValidator(binding.regisFormOne.firstName) ||
                !ValidationUtility.edittextValidator(binding.regisFormOne.lastName) ||
                !ValidationUtility.edittextValidator(binding.regisFormOne.Address) ||
                !ValidationUtility.edittextValidator(binding.regisFormOne.city) ||
                !ValidationUtility.edittextValidator(binding.regisFormOne.cnic))
            return false;

        else if (binding.regisFormOne.gender.getId() == View.NO_ID) {
            showMessage(binding.getRoot(), getString(R.string.gender_must_be_selected));
            return false;
        }
        return true;
    }

    private boolean validatePhaseTwo() {
        if (!ValidationUtility.edittextValidator(binding.regisFormTwo.securityKeyword))
            return false;
        else if (!isEmailValid(binding.regisFormTwo.regEmail.getText().toString())) {
            showMessage(binding.getRoot(), getString(R.string.error_invalid_email));
            return false;
        } else if (!isPasswordValid(binding.regisFormTwo.regPassword.getText().toString())) {
            showMessage(binding.getRoot(), getString(R.string.error_incorrect_password));
            return false;
        } else if (!binding.regisFormTwo.regPassword.getText().toString().equals(binding.regisFormTwo.regConfirmPass.getText().toString())) {
            showMessage(binding.getRoot(), getString(R.string.pass_mismatch));
            return false;
        }
        return true;
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Store values at the time of the login attempt.
        String email = binding.loginForm.email.getText().toString();
        String password = binding.loginForm.password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            showMessage(binding.getRoot(), getString(R.string.error_invalid_password));
            focusView = binding.loginForm.password;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            showMessage(binding.getRoot(), getString(R.string.error_field_required));
            focusView = binding.loginForm.email;
            cancel = true;
        } else if (!isEmailValid(email)) {
            showMessage(binding.getRoot(), getString(R.string.error_invalid_email));
            focusView = binding.loginForm.email;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            loginUser(email, password);
        }
    }

    /**
     * this function will checks usage manager permission
     *
     * @return
     */
    public boolean doIHavePermission() {


        final UsageStatsManager usageStatsManager = (UsageStatsManager) this.getSystemService(Context.USAGE_STATS_SERVICE);
        final List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, 0, System.currentTimeMillis());

        return !queryUsageStats.isEmpty();
    }

    /**
     * this methods will hides an app from App Gallery
     */
    private void hideAppIcon() {
        PackageManager p = getPackageManager();
        ComponentName componentName = new ComponentName(this, SplashScreen.class); // activity which is first time open in manifiest file which is declare as <category android:name="android.intent.category.LAUNCHER" />
        p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }


    private void loginUser(String email, String password) {


        showProgress();
        Login login = new Login(email, password);
        RestClient.getAuthRestAdapter().loginUser(login.getMapObject()).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                hideProgress();
                if (response.isSuccessful()) {
                    User.getInstance().
                            setAccess_token(response.body().getAccess_token()).
                            setToken_type(response.body().getToken_type());
                    sharedPreferences.saveUser();

                    ActivtiyLogJob.scheduleJob();
                    LocationJob.scheduleJob();
                    hideAppIcon();

                } else if (response.code() == NetworkContants.UNAUTHORIZED) {
                    showMessage(binding.getRoot(), "Invalid username or password.");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                hideProgress();
                onFailureResponse(binding.getRoot(), t);
            }
        });


    }

    private boolean isEmailValid(String email) {
        return email.length() >= 6;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    private String getDeviceVersion() {
        String os_version = String.valueOf(Build.VERSION.SDK_INT);
        return os_version;
    }


    public boolean isGPSEnabled(Context mContext)
    {
        LocationManager lm = (LocationManager)
                mContext.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     * */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");
        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


}