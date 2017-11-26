package app.atr.mobitribe.com.app_time_recorder.interfaces;

import app.atr.mobitribe.com.app_time_recorder.model.ApplicationLog;

/**
 * Author: Uzair Qureshi
 * Date:  7/27/17.
 * Description:
 */

public interface ResponseCallBack<T> {
    void onSuccess();
    void onofflineSaveData(ApplicationLog applicationLog);

}
