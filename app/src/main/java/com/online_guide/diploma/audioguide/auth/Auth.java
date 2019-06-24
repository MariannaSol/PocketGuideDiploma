package com.online_guide.diploma.audioguide.auth;


import android.content.Context;
import android.telephony.TelephonyManager;

public class Auth {

    private TelephonyManager manager;

    private Context context;

        public Auth(Context context) {
        this.context = context;
        manager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
    }

    public String getDevicedId() {

        return manager.getDeviceId();
    }
}
