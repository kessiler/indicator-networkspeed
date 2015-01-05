package br.com.indicator.networkspeed;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.widget.RemoteViews;
import android.widget.Toast;

public class TrafficWidget extends AppWidgetProvider {

    private boolean mAttached;
    private BroadcastReceiver mIntentReceiver;
    private TrafficSpeed mTrafficSpeed;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        if (!mAttached) {
            mAttached = true;
            mIntentReceiver = new TrafficReceiver();
            mTrafficSpeed = new TrafficSpeed(context);
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            context.registerReceiver(mIntentReceiver, filter);
        } else {
            if (mTrafficSpeed.isNetworkConnected()) {
                String textSpeed = "Download: " + mTrafficSpeed.getFormattedDownSpeed() + " / Upload: " + mTrafficSpeed.getFormattedUpSpeed();
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.traffic_widget);
                remoteViews.setTextViewText(R.id.textView, textSpeed);
                ComponentName trafficComponent = new ComponentName(context, TrafficWidget.class);
                appWidgetManager.updateAppWidget(trafficComponent, remoteViews);
            }
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        if (mAttached) {
            context.unregisterReceiver(mIntentReceiver);
            mAttached = false;
        }
    }

}
