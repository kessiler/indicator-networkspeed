package br.com.indicator.networkspeed;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.text.DecimalFormat;
import java.util.Locale;

public class TrafficSpeed {

    private final static float MB_SIZE = 1048576f;
    private final static float KB_SIZE = 1024f;
    private final static DecimalFormat decimalFormat = new DecimalFormat("##0.0");
    private static final String TAG = TrafficSpeed.class.getSimpleName();
    private Context context;
    private final TrafficHandler mTrafficHandler = new TrafficHandler();

    public TrafficSpeed(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public String getFormattedDownSpeed() {
        return format(mTrafficHandler.getDownSpeed());
    }

    public String getFormattedUpSpeed() {
        return format(mTrafficHandler.getUpSpeed());
    }

    public boolean isNetworkConnected() {
        Log.d(TAG, "isNetworkConnected");
        boolean networkConnected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            networkConnected = networkInfo.isConnected();
            Log.d(TAG, "networkType = " + networkInfo.getTypeName().toUpperCase(Locale.ENGLISH));
        }
        return networkConnected;
    }

    public String format(float speed) {
        float value;
        String unit;
        if (speed / MB_SIZE >= 1) {
            value = speed / MB_SIZE;
            unit = "MB/s";
        } else if (speed / KB_SIZE >= 1) {
            value = speed / KB_SIZE;
            unit = "KB/s";
        } else {
            value = speed;
            unit = "B/s";
        }
        return decimalFormat.format(value) + unit;
    }
}
