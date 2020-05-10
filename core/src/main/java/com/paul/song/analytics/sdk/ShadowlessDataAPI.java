package com.paul.song.analytics.sdk;

import android.app.Application;
import android.os.Build;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import org.json.JSONObject;
import java.util.Map;

@Keep
public class ShadowlessDataAPI {
    private final String TAG = this.getClass().getSimpleName();
    public static final String SDK_VERSION = "1.0.0";
    private static ShadowlessDataAPI INSTANCE;
    private static final Object mLock = new Object();
    private static Map<String, Object> mDeviceInfo;
    private String mDeviceId;
    private static boolean isDebugable = false;

    @Keep
    @SuppressWarnings("UnusedReturnValue")
    public static ShadowlessDataAPI init(Application application) {
        synchronized (mLock) {
            if (null == INSTANCE) {
                INSTANCE = new ShadowlessDataAPI(application);
            }
            return INSTANCE;
        }
    }

    @Keep
    public static ShadowlessDataAPI getInstance() {
        return INSTANCE;
    }

    private ShadowlessDataAPI(Application application) {
        mDeviceId = ShadowlessDataPrivate.getAndroidID(application.getApplicationContext());
        mDeviceInfo = ShadowlessDataPrivate.getDeviceInfo(application.getApplicationContext());

        ShadowlessDataPrivate.registerActivityLifecycleCallbacks(application);

    }

    public static void setDebugMode(boolean flag) {
        isDebugable = flag;
    }

    /**
     * Track 事件
     *
     * @param eventName  String 事件名称
     * @param properties JSONObject 事件属性
     */
    @Keep
    public void track(@NonNull final String eventName, @Nullable JSONObject properties) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("event", eventName);
            jsonObject.put("device_id", mDeviceId);
            jsonObject.put("sysVn", Build.ID == null ? "UNKNOWN" : Build.ID);

            JSONObject sendProperties = new JSONObject(mDeviceInfo);

            if (properties != null) {
                ShadowlessDataPrivate.mergeJSONObject(properties, sendProperties);
            }

            jsonObject.put("properties", sendProperties);
            jsonObject.put("time", System.currentTimeMillis());

            if (isDebugable) {
                Log.i(TAG, ShadowlessDataPrivate.formatJson(jsonObject.toString()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
