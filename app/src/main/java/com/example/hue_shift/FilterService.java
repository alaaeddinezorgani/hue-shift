package com.example.hue_shift;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.WindowManager;

public class FilterService extends Service {
    private static FilterService instance;
    private static int filterIntensity = 50;

    private WindowManager windowManager;
    private OverlayView overlayView;

    public static void setFilterIntensity(int intensity) {
        filterIntensity = intensity;
        if (instance != null && instance.overlayView != null)
            instance.overlayView.setFilterIntensity(intensity);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        overlayView = new OverlayView(this, filterIntensity);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT
        );

        params.gravity = Gravity.TOP | Gravity.START;
        windowManager.addView(overlayView, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (overlayView != null) {
            windowManager.removeView(overlayView);
            overlayView = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // not a bound service
    }
}
