package com.example.gentl.servio.Helpful;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkChangeReceiver extends BroadcastReceiver {

    public NetworkChangeReceiver.DelegateNetworkChange delegate = null;

    public NetworkChangeReceiver(DelegateNetworkChange delegate){
        this.delegate = delegate;
    }

    public NetworkChangeReceiver(){
    }

    public interface DelegateNetworkChange {
        void networkAvailable(boolean output);
    }

    public boolean getConnectivityStatus(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager)
                ctx.getSystemService (Context.CONNECTIVITY_SERVICE);

        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (null != ni) {
            if(ni.getType() == ConnectivityManager.TYPE_WIFI || ni.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final android.net.NetworkInfo mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isConnected() || mobile.isConnected())
        {
            //Network Available
            if(delegate != null) delegate.networkAvailable(true);
        }
        else
        {
            delegate.networkAvailable(false);
        }
    }
}
