package com.kreasihebatindonesia.remboeg.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;

import com.kreasihebatindonesia.remboeg.networks.ConnectivityReceiver;
import com.kreasihebatindonesia.remboeg.utils.TypefaceUtil;

/**
 * Created by IT DCM on 06/11/2017.
 */

public class BaseApplication extends Application {
    private static BaseApplication mInstance;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Bariol_Regular.otf");

    }

    public static synchronized BaseApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
