package com.r21nomi.androidanimationexperiment;

import android.os.Build;

/**
 * Created by r21nomi on 2016/12/11.
 */

public class DeviceUtil {
    public static boolean isOverLollipop() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP;
    }
}
