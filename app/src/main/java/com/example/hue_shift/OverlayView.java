package com.example.hue_shift;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class OverlayView extends View {
    private int filterColor;
    private final Paint paint;

    public OverlayView(Context context, int initialIntensity) {
        super(context);
        paint = new Paint();
        setFilterIntensity(initialIntensity);
    }

    protected void setFilterIntensity(int intensity) {
        // slider's 0-100 to alpha 0-255
        int alpha = (int) (intensity * 2.55);
        filterColor = Color.argb(alpha, 255, 85, 0);
        invalidate(); // redraw with new color
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(filterColor);
    }
}
