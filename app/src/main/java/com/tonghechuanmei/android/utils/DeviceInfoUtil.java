/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTPig
 * Author：马靖乘
 * Date：18-11-30 下午5:35
 */

package com.tonghechuanmei.android.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.FileReader;


public class DeviceInfoUtil {

    private static String deviceId;

    public static String getAppName(Context context) {
        try {
            final PackageManager pm = context.getPackageManager();
            ApplicationInfo ai;
            ai = pm.getApplicationInfo(getPackageName(context), 0);
            return (String) (ai != null ? pm.getApplicationLabel(ai) : "unknown");
        } catch (Exception e) {
        }
        return "unknown";
    }

    /**
     * 获取应用版本号
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static int getAppVersionCode(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 获取应用版本名称
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static String getAppVersionName(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            String verName = pi.versionName;
            if (verName.split(".").length > 3) {
                verName = verName.substring(0, verName.lastIndexOf("."));
            }
            return verName.replace(".", "");
        } catch (Exception e) {
        }
        return "unknown";
    }

    /**
     * 获取包名
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        try {
            return context.getPackageName();
        } catch (Exception e) {
        }
        return "";
    }


    /**
     * 获取总内存
     *
     * @return
     */
    private static int getTotalMemory() {
        String str1 = "/proc/meminfo";
        String str2;
        String[] arrayOfString;
        int initial_memory = 0;

        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            initial_memory = Integer.valueOf(arrayOfString[1]) / 1024;
            localBufferedReader.close();
        } catch (Exception e) {
        }
        return initial_memory;
    }

    /**
     * 获取MAC地址
     *
     * @param context
     * @return
     */
    public static String getLocalMacAddress(Context context) {
        String mac = "";
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();

            if (null == info) {
                return "";
            }
            mac = info.getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mac;
    }


    private static Bundle getMetaData(Context context) throws Exception {
        ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        Bundle bundle = ai.metaData;
        if (bundle == null) {
            bundle = new Bundle();
        }
        return bundle;
    }


    private static String getProvidersName(String IMSI) {
        String ProvidersName = null;

        if (null == IMSI) {
            return "4";
        }
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
            ProvidersName = "1";
        } else if (IMSI.startsWith("46001") || IMSI.startsWith("46006")) {
            ProvidersName = "2";
        } else if (IMSI.startsWith("46003") || IMSI.startsWith("46005")) {
            ProvidersName = "3";
        } else {
            ProvidersName = "0";
        }
        return ProvidersName;
    }


    /**
     * @return NETWORK_STATE_TYPE = wifi | 2G | 3G | 4G | VPN | BLUETOOTH | UNKNOWN
     */
    public static int getNetworkType(Context context) {
        int type = 0;
        if (context != null) {
            ConnectivityManager cm = null;
            try {
                cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (cm != null && cm.getActiveNetworkInfo() != null) {
                    if (cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_MOBILE) {
                        type = cm.getActiveNetworkInfo().getSubtype();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return type;
    }

}
