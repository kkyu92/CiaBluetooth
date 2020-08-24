package com.example.ciabluetooth.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.ciabluetooth.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SharedPreferencesPackage {
    public static final String TAG = "SharedPackage";

    // 월별 사용량 저장
    // year + month (ex. 202007) 를 key 뒤에 붙여 월별 저장 사용
    // TODO: brush / cooler / puff / silicon
    public static void setBrushThisMonth(Context context, int brushCount) {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat ymFormat = new SimpleDateFormat("yyyyMM", Locale.getDefault());
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String ym = ymFormat.format(currentTime);
        editor.putInt(Constants.BRUSH + ym, brushCount);
        editor.apply();
    }

    public static void setCoolerThisMonth(Context context, int coolerCount) {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat ymFormat = new SimpleDateFormat("yyyyMM", Locale.getDefault());
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String ym = ymFormat.format(currentTime);
        editor.putInt(Constants.COOLER + ym, coolerCount);
        editor.apply();
    }

    public static void setPuffThisMonth(Context context, int puffCount) {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat ymFormat = new SimpleDateFormat("yyyyMM", Locale.getDefault());
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String ym = ymFormat.format(currentTime);
        editor.putInt(Constants.PUFF + ym, puffCount);
        editor.apply();
    }

    public static void setSiliconThisMonth(Context context, int siliconCount) {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat ymFormat = new SimpleDateFormat("yyyyMM", Locale.getDefault());
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String ym = ymFormat.format(currentTime);
        editor.putInt(Constants.SILICON + ym, siliconCount);
        editor.apply();
    }

    public static void setAllThisMonth(Context context, int allCount) {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat ymFormat = new SimpleDateFormat("yyyyMM", Locale.getDefault());
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String ym = ymFormat.format(currentTime);
        editor.putInt(Constants.ALL + ym, allCount);
        editor.apply();
    }

    // 이번달 사용량 = 받은 DATA - 지난달 사용량 (getBrushCountLastMonth)
    // 지난달 사용량 가져오기
    public static int getBrushLastMonth(Context context) {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat mFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat yFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String year = yFormat.format(currentTime);
        String month = mFormat.format(currentTime);
        String ym = null;
        if (month.equals("01")) {
            ym = year + "12";
        } else {
            int m = Integer.parseInt(month) - 1;
            String mText = String.valueOf(m);
            if (mText.length() == 1) {
                mText = "0" + mText;
                ym = year + mText;
            } else {
                ym = year + mText;
            }
        }
        return preferences.getInt(Constants.BRUSH + ym, 0);
    }

    public static int getCoolerLastMonth(Context context) {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat mFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat yFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String year = yFormat.format(currentTime);
        String month = mFormat.format(currentTime);
        String ym = null;
        if (month.equals("01")) {
            ym = year + "12";
        } else {
            int m = Integer.parseInt(month) - 1;
            String mText = String.valueOf(m);
            if (mText.length() == 1) {
                mText = "0" + mText;
                ym = year + mText;
            } else {
                ym = year + mText;
            }
        }
        return preferences.getInt(Constants.COOLER + ym, 0);
    }

    public static int getPuffLastMonth(Context context) {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat mFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat yFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String year = yFormat.format(currentTime);
        String month = mFormat.format(currentTime);
        String ym = null;
        if (month.equals("01")) {
            ym = year + "12";
        } else {
            int m = Integer.parseInt(month) - 1;
            String mText = String.valueOf(m);
            if (mText.length() == 1) {
                mText = "0" + mText;
                ym = year + mText;
            } else {
                ym = year + mText;
            }
        }
        return preferences.getInt(Constants.PUFF + ym, 0);
    }

    public static int getSiliconLastMonth(Context context) {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat mFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat yFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String year = yFormat.format(currentTime);
        String month = mFormat.format(currentTime);
        String ym = null;
        if (month.equals("01")) {
            ym = year + "12";
        } else {
            int m = Integer.parseInt(month) - 1;
            String mText = String.valueOf(m);
            if (mText.length() == 1) {
                mText = "0" + mText;
                ym = year + mText;
            } else {
                ym = year + mText;
            }
        }
        return preferences.getInt(Constants.SILICON + ym, 0);
    }

    public static int getAllLastMonth(Context context) {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat mFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat yFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String year = yFormat.format(currentTime);
        String month = mFormat.format(currentTime);
        String ym = null;
        if (month.equals("01")) {
            ym = year + "12";
        } else {
            int m = Integer.parseInt(month) - 1;
            String mText = String.valueOf(m);
            if (mText.length() == 1) {
                mText = "0" + mText;
                ym = year + mText;
            } else {
                ym = year + mText;
            }
        }
        return preferences.getInt(Constants.ALL + ym, 0);
    }

    // 이번달 사용량 가져오기
    public static int getBrushThisMonth(Context context) {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat ymFormat = new SimpleDateFormat("yyyyMM", Locale.getDefault());
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String ym = ymFormat.format(currentTime);
        return preferences.getInt(Constants.BRUSH + ym, 0);
    }

    public static int getCoolerThisMonth(Context context) {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat ymFormat = new SimpleDateFormat("yyyyMM", Locale.getDefault());
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String ym = ymFormat.format(currentTime);
        return preferences.getInt(Constants.COOLER + ym, 0);
    }

    public static int getPuffThisMonth(Context context) {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat ymFormat = new SimpleDateFormat("yyyyMM", Locale.getDefault());
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String ym = ymFormat.format(currentTime);
        return preferences.getInt(Constants.PUFF + ym, 0);
    }

    public static int getSiliconThisMonth(Context context) {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat ymFormat = new SimpleDateFormat("yyyyMM", Locale.getDefault());
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String ym = ymFormat.format(currentTime);
        return preferences.getInt(Constants.SILICON + ym, 0);
    }

    public static int getAllThisMonth(Context context) {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat ymFormat = new SimpleDateFormat("yyyyMM", Locale.getDefault());
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String ym = ymFormat.format(currentTime);
        return preferences.getInt(Constants.ALL + ym, 0);
    }
}
