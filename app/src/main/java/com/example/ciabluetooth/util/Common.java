package com.example.ciabluetooth.util;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

public class Common {

    public static int getAddLinear(Context context, int percent, float dp) {
        int addHeight = 0;
        if (percent <= 80 && percent > 60) {
            addHeight = Common.dpToPixel(context, dp);
        } else if (percent <= 60 && percent > 40) {
            addHeight = Common.dpToPixel(context, dp) * 2;
        } else if (percent <= 40 && percent > 20) {
            addHeight = Common.dpToPixel(context, dp) * 3;
        } else if (percent <= 20 && percent > 0) {
            addHeight = Common.dpToPixel(context, dp) * 4;
        }
        return addHeight;
    }

    public static int getAddWidth( int percent) {
        int addWidth = 0;
        if (percent <= 100 && percent > 80) {
            addWidth = 70;
        } else if (percent <= 80 && percent > 60) {
            addWidth = 60;
        } else if (percent <= 60 && percent > 40) {
            addWidth = 50;
        } else if (percent <= 40 && percent > 20) {
            addWidth = 40;
        } else if (percent <= 20 && percent > 0) {
            addWidth = 30;
        }
        return addWidth;
    }

    private static int dpToPixel(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    // percent line
    public static void setPercentGaugeWidth( final View defaultImageView, final View imageView, final int percent) {
        try {
            ViewTreeObserver observer = defaultImageView.getViewTreeObserver();
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    ViewGroup.LayoutParams params = imageView.getLayoutParams();
                    int width = defaultImageView.getWidth() /  100;
                    params.width =  (width * (percent)) + Common.getAddWidth(percent);
                    Log.e("params.width", ""+params.width);
                    Log.e("width*percent", ""+width*percent);
                    imageView.setLayoutParams(params);
                    defaultImageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        } catch (Exception ignored) {
            setPercentGaugeWidth(defaultImageView, imageView, percent);
        }
    }

    public static void changeProgressView(int viewWidth, int viewHeight, View view) {
        // 3:4 // 480:640 // 960:1280 // 1080:1440
        ViewGroup.LayoutParams params = view.getLayoutParams();
            params.width = viewWidth;
            params.height = viewWidth;
            Log.e("Common Size", "progressSize ::: " + (int) viewWidth + " || " + viewHeight);
        view.setLayoutParams(params);
    }
}
