package br.com.indicator.networkspeed;

import android.net.TrafficStats;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;


public class TrafficHandler extends Handler {

    private long downSpeed;
    private long upSpeed;
    private long totalRxBytes;
    private long totalTxBytes;
    private long lastUpdateTime;

    public long getDownSpeed() {
        return downSpeed;
    }

    public long getUpSpeed() {
        return upSpeed;
    }

    @Override
    public void handleMessage(Message msg) {
        long td = SystemClock.elapsedRealtime() - lastUpdateTime;
        if (td == 0) {
            return;
        }
        downSpeed = (TrafficStats.getTotalRxBytes() - totalRxBytes) * 1000 / td;
        upSpeed = (TrafficStats.getTotalTxBytes() - totalTxBytes) * 1000 / td;
        totalRxBytes = TrafficStats.getTotalRxBytes();
        totalTxBytes = TrafficStats.getTotalRxBytes();
        lastUpdateTime = SystemClock.elapsedRealtime();

        removeCallbacks(mRunnable);
        postDelayed(mRunnable, 1000);
        super.handleMessage(msg);
    }

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            TrafficHandler.this.sendEmptyMessage(0);
        }
    };
}