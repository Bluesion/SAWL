package com.charlie.sawl.tools;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import java.util.Objects;

public class Tools {
    public static boolean isWifi(Context mContext) {
        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = Objects.requireNonNull(manager).getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return wifi.isConnected();
    }

    public static boolean isOnline(Context mContext) {
        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = Objects.requireNonNull(manager).getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return wifi.isConnected() || mobile.isConnected();
    }

    public static void restart(Activity activity, int delay) {
        if (delay == 0) {
            delay = 1;
        }
        Intent restartIntent = activity.getPackageManager().getLaunchIntentForPackage(activity.getPackageName());
        Objects.requireNonNull(restartIntent).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent intent = PendingIntent.getActivity(activity, 0, restartIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager manager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        Objects.requireNonNull(manager).set(AlarmManager.RTC, System.currentTimeMillis() + delay, intent);
        finishAffinity(activity);
    }

    private static void finishAffinity(final Activity activity) {
        activity.setResult(Activity.RESULT_CANCELED);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.finishAffinity();
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.finishAffinity();
                }
            });
        }
    }
}