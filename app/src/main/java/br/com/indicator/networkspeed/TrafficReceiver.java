package br.com.indicator.networkspeed;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;


public class TrafficReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            Log.d("traffic", "send a broadcast");
            final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName trafficWidget = new ComponentName(context, TrafficWidget.class);
            int[] widgetIds = appWidgetManager.getAppWidgetIds(trafficWidget);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIds);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            context.sendBroadcast(intent);
        }
    }
}
