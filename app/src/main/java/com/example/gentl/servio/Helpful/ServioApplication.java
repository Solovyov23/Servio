package com.example.gentl.servio.Helpful;

import android.app.Application;

import com.example.gentl.servio.Helpful.NetworkHelper;

public class ServioApplication extends Application
{
    public void onCreate ()
    {
        super.onCreate();
        NetworkHelper.SubscribeToNetworkEvents(getApplicationContext());
    }
}
