package com.paul.song.analytics.sdk;

import android.os.SystemProperties;

public class SystemConstants {

    public static final String XXX = getProp("xxxxxx", "UNKNOWN");

    private static String getProp(String k, String def) {
        return SystemProperties.get(k, def);
    }
}
