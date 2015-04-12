package br.com.indicator.networkspeed;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.RemoteViews;

public class TrafficWidget extends AppWidgetProvider {
    private boolean mAttached;
    private TrafficSpeed mTrafficSpeed;
    private BroadcastReceiver mIntentReceiver;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        Resources resources = context.getResources();
        String textSpeed = resources.getString(R.string.waitingConnection);
        if (mTrafficSpeed.isNetworkConnected()) {
            textSpeed = resources.getString(R.string.download) + ": " + mTrafficSpeed.getFormattedDownSpeed() + " / " +
                    resources.getString(R.string.upload) + ": " + mTrafficSpeed.getFormattedUpSpeed();
        }
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.traffic_widget);
        remoteViews.setTextViewText(R.id.textView, textSpeed);
        ComponentName trafficComponent = new ComponentName(context, TrafficWidget.class);
        appWidgetManager.updateAppWidget(trafficComponent, remoteViews);
    }

    @Override
    public void onEnabled(Context context) {
        Log.d("traffic", "onEnabled - teste");
        mTrafficSpeed = new TrafficSpeed(context);
        mIntentReceiver = new TrafficReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.getApplicationContext().registerReceiver(mIntentReceiver, filter);

    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Log.d("traffic", "onDeleted - teste");
        context.getApplicationContext().unregisterReceiver(mIntentReceiver);
    }

}
