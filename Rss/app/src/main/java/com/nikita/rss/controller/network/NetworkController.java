package com.nikita.rss.controller.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkController {

    public static NetworkState getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return NetworkState.WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return NetworkState.MOBILE;
        }
        return NetworkState.NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        NetworkState conn = NetworkController.getConnectivityStatus(context);
        String status = null;
        if (conn == NetworkState.WIFI) {
            status = "Wifi enabled";
        } else if (conn == NetworkState.MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == NetworkState.NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }
}
