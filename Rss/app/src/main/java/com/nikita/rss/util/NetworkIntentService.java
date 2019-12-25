package com.nikita.rss.util;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class NetworkIntentService extends IntentService {

    public NetworkIntentService() {
        super("NetworkIntentService");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        Bundle bundle = workIntent.getExtras();
        if (bundle == null) {
            return;
        }
        Messenger messenger = (Messenger) bundle.get("messenger");
        while (true) {
            try {
                ConnectivityManager cm =
                        (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnected();

                Message msg = Message.obtain();
                Bundle data = new Bundle();
                data.putBoolean("isConnected", isConnected);
                msg.setData(data);
                messenger.send(msg);
                Thread.sleep(3000);
            }
            catch (InterruptedException | RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
