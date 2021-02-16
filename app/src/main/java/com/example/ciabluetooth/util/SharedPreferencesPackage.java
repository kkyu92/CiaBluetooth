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
    private static Date currentTime = Calendar.getInstance().getTime();
    private static SimpleDateFormat ymFormat = new SimpleDateFormat("yyyyMM", Locale.getDefault());
    private static SimpleDateFormat mFormat = new SimpleDateFormat("MM", Locale.getDefault());
    private static SimpleDateFormat yFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

    public static void setDummy(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt("brushSNRun202009", 300);
        editor.putInt("brushSNCnt202009", 300);
        editor.putInt("brushSNRun202010", 300);
        editor.putInt("brushSNCnt2020010", 300);

        editor.putInt("coolerSNRun202009", 300);
        editor.putInt("coolerSNCnt202009", 300);
        editor.putInt("coolerSNRun202010", 300);
        editor.putInt("coolerSNCnt202010", 300);

        editor.putInt("puffSNRun202009", 300);
        editor.putInt("puffSNCnt202009", 300);
        editor.putInt("puffSNRun202010", 300);
        editor.putInt("puffSNCnt202010", 300);

        editor.putInt("siliconSNRun202009", 300);
        editor.putInt("siliconSNCnt202009", 300);
        editor.putInt("siliconSNRun202010", 300);
        editor.putInt("siliconSNCnt202010", 300);
        editor.apply();
    }

    public static void setNowActivityName(Context context, String activityName) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("activity", activityName);
        editor.apply();
    }

    public static void setIsConnected(Context context, boolean isConnected) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("isConnected", isConnected);
        editor.apply();
    }

    public static String getNowActivityName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        return preferences.getString("activity", "");
    }

    public static Boolean getIsConnected(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        return preferences.getBoolean("isConnected", false);
    }
    // 월별 사용량 저장
    // year + month (ex. 202007) 를 key 뒤에 붙여 월별 저장 사용
    // TODO: brush / cooler / puff / silicon
    public static void setAllData(Context context, String deviceSN, int deviceFual, String deviceRun, String deviceCnt, int headType, String brushSN, String brushRun, String brushCnt, String coolerSN, String coolerRun, String coolerCnt, String puffSN, String puffRun, String puffCnt, String siliconSN, String siliconRun, String siliconCnt) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String month = mFormat.format(currentTime);
        String year = yFormat.format(currentTime);
        // fake data
        //TODO: month => (String + 월)로 key 값 구분 1 ~ 12 값이 있다면 저장
//        brushSN = "brushSN";
//        coolerSN = "coolerSN";
//        puffSN = "puffSN";
//        siliconSN = "siliconSN";

        int brushRunTM = 0, brushCntTM = 0, coolerRunTM = 0, coolerCntTM = 0, puffRunTM = 0, puffCntTM = 0, siliconRunTM = 0, siliconCntTM = 0;

        int getYear = preferences.getInt(Constants.YEAR, 2020);
        int thisYear = Integer.parseInt(year);

        for (int y = getYear; y <= thisYear; y++) {
            String yText = String.valueOf(y);
            for (int m = 1; m < 13; m++) {
                String mText = String.valueOf(m);
                if (m < 10) {
                    mText = "0" + mText;
                }
                // !This Year
                if (!String.valueOf(thisYear).equals(yText)) {
                    brushRunTM += preferences.getInt(brushSN + "Run" + yText + mText, 0);
                    brushCntTM += preferences.getInt(brushSN + "Cnt" + yText + mText, 0);
                    coolerRunTM += preferences.getInt(coolerSN + "Run" + yText + mText, 0);
                    coolerCntTM += preferences.getInt(coolerSN + "Cnt" + yText + mText, 0);
                    puffRunTM += preferences.getInt(puffSN + "Run" + yText + mText, 0);
                    puffCntTM += preferences.getInt(puffSN + "Cnt" + yText + mText, 0);
                    siliconRunTM += preferences.getInt(siliconSN + "Run" + yText + mText, 0);
                    siliconCntTM += preferences.getInt(siliconSN + "Cnt" + yText + mText, 0);
                } else {
                    // This Year
                    if (!month.equals(mText)) {
                        brushRunTM += preferences.getInt(brushSN + "Run" + yText + mText, 0);
                        brushCntTM += preferences.getInt(brushSN + "Cnt" + yText + mText, 0);
                        coolerRunTM += preferences.getInt(coolerSN + "Run" + yText + mText, 0);
                        coolerCntTM += preferences.getInt(coolerSN + "Cnt" + yText + mText, 0);
                        puffRunTM += preferences.getInt(puffSN + "Run" + yText + mText, 0);
                        puffCntTM += preferences.getInt(puffSN + "Cnt" + yText + mText, 0);
                        siliconRunTM += preferences.getInt(siliconSN + "Run" + yText + mText, 0);
                        siliconCntTM += preferences.getInt(siliconSN + "Cnt" + yText + mText, 0);
                    }
                }
            }
        }
        Log.e("Shared", siliconCntTM + " <-siliconThisMonth");
        editor.putString(Constants.DEVICE_SN, deviceSN);
        editor.putInt(deviceSN + "Fual", deviceFual);
        editor.putInt(deviceSN + "Run", Integer.parseInt(deviceRun, 16));
        editor.putInt(deviceSN + "Cnt", Integer.parseInt(deviceCnt, 16));
        if (headType != 0) {
            editor.putInt(deviceSN + "HeadType", headType);
        }
        editor.putString(Constants.BRUSH_SN, brushSN);
        editor.putInt(brushSN + "RunTotal", Integer.parseInt(brushRun, 16));
        editor.putInt(brushSN + "Run" + year + month, Integer.parseInt(brushRun, 16) - brushRunTM);
        editor.putInt(brushSN + "CntTotal", Integer.parseInt(brushCnt, 16));
        editor.putInt(brushSN + "Cnt" + year + month, Integer.parseInt(brushCnt, 16) - brushCntTM);
        editor.putString(Constants.COOLER_SN, coolerSN);
        editor.putInt(coolerSN + "RunTotal", Integer.parseInt(coolerRun, 16));
        editor.putInt(coolerSN + "Run" + year + month, Integer.parseInt(coolerRun, 16) - coolerRunTM);
        editor.putInt(coolerSN + "CntTotal", Integer.parseInt(coolerCnt, 16));
        editor.putInt(coolerSN + "Cnt" + year + month, Integer.parseInt(coolerCnt, 16) - coolerCntTM);
        editor.putString(Constants.PUFF_SN, puffSN);
        editor.putInt(puffSN + "RunTotal", Integer.parseInt(puffRun, 16));
        editor.putInt(puffSN + "Run" + year + month, Integer.parseInt(puffRun, 16) - puffRunTM);
        editor.putInt(puffSN + "CntTotal", Integer.parseInt(puffCnt, 16));
        editor.putInt(puffSN + "Cnt" + year + month, Integer.parseInt(puffCnt, 16) - puffCntTM);
        editor.putString(Constants.SILICON_SN, siliconSN);
        editor.putInt(siliconSN + "RunTotal", Integer.parseInt(siliconRun, 16));
        editor.putInt(siliconSN + "Run" + year + month, Integer.parseInt(siliconRun, 16) - siliconRunTM);
        editor.putInt(siliconSN + "CntTotal", Integer.parseInt(siliconCnt, 16));
        editor.putInt(siliconSN + "Cnt" + year + month, Integer.parseInt(siliconCnt, 16) - siliconCntTM);
        editor.apply();
    }

    public static void setDeviceAddress(Context context, String deviceAddress) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.DEVICE_ADDRESS, deviceAddress);
        editor.apply();
    }

    // get deviceInfo
    public static String getDeviceID(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        return preferences.getString(Constants.DEVICE_SN, "");
    }
    public static String getDeviceAddress(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        return preferences.getString(Constants.DEVICE_ADDRESS, "");
    }
    public static int getDeviceRun(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String deviceSN = preferences.getString(Constants.DEVICE_SN, "");
        return preferences.getInt(deviceSN + "Run", 0);
    }
    public static int getDeviceBattery(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String deviceSN = preferences.getString(Constants.DEVICE_SN, "");
        return preferences.getInt(deviceSN + "Fual", 0);
    }
    public static int getHeadType(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String deviceSN = preferences.getString(Constants.DEVICE_SN, "");
        return preferences.getInt(deviceSN + "HeadType", 0);
    }
    public static String getBrushID(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        return preferences.getString(Constants.BRUSH_SN, "");
    }
    public static String getCoolerID(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        return preferences.getString(Constants.COOLER_SN, "");
    }
    public static String getPuffID(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        return preferences.getString(Constants.PUFF_SN, "");
    }
    public static String getSiliconID(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        return preferences.getString(Constants.SILICON_SN, "");
    }

    // Total Brush Run + Cnt
    public static int getBrushRunTotal(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String brushSN = preferences.getString(Constants.BRUSH_SN, "");
        return preferences.getInt(brushSN + "RunTotal", 0);
    }
    public static int getBrushCntTotal(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String brushSN = preferences.getString(Constants.BRUSH_SN, "");
        return preferences.getInt(brushSN + "CntTotal", 0);
    }

    // Total Cooler Run + Cnt
    public static int getCoolerRunTotal(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String coolerSN = preferences.getString(Constants.COOLER_SN, "");
        return preferences.getInt(coolerSN + "RunTotal", 0);
    }
    public static int getCoolerCntTotal(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String coolerSN = preferences.getString(Constants.COOLER_SN, "");
        return preferences.getInt(coolerSN + "CntTotal", 0);
    }

    // Total Puff Run + Cnt
    public static int getPuffRunTotal(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String puffSN = preferences.getString(Constants.PUFF_SN, "");
        return preferences.getInt(puffSN + "RunTotal", 0);
    }
    public static int getPuffCntTotal(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String puffSN = preferences.getString(Constants.PUFF_SN, "");
        return preferences.getInt(puffSN + "CntTotal", 0);
    }

    // Total Silicon Run + Cnt
    public static int getSiliconRunTotal(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String siliconSN = preferences.getString(Constants.SILICON_SN, "");
        return preferences.getInt(siliconSN + "RunTotal", 0);
    }
    public static int getSiliconCntTotal(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String siliconSN = preferences.getString(Constants.SILICON_SN, "");
        return preferences.getInt(siliconSN + "CntTotal", 0);
    }

    // 지난달 사용량 가져오기
    // brush Run + Cnt
    public static int getBrushRunLastMonth(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String brushSN = preferences.getString(Constants.BRUSH_SN, "");
        String ym = ymChanger();
        return preferences.getInt(brushSN + "Run" + ym, 0);
    }
    public static int getBrushCntLastMonth(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String brushSN = preferences.getString(Constants.BRUSH_SN, "");
        String ym = ymChanger();
        return preferences.getInt(brushSN + "Cnt" + ym, 0);
    }

    // cooler Run + Cnt
    public static int getCoolerRunLastMonth(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String coolerSN = preferences.getString(Constants.COOLER_SN, "");
        String ym = ymChanger();
        return preferences.getInt(coolerSN + "Run" + ym, 0);
    }
    public static int getCoolerCntLastMonth(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String coolerSN = preferences.getString(Constants.COOLER_SN, "");
        String ym = ymChanger();
        return preferences.getInt(coolerSN + "Cnt" + ym, 0);
    }

    // puff Run + Cnt
    public static int getPuffRunLastMonth(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String puffSN = preferences.getString(Constants.PUFF_SN,"");
        String ym = ymChanger();
        return preferences.getInt(puffSN + "Run" + ym, 0);
    }
    public static int getPuffCntLastMonth(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String puffSN = preferences.getString(Constants.PUFF_SN,"");
        String ym = ymChanger();
        return preferences.getInt(puffSN + "Cnt" + ym, 0);
    }

    // silicon Run + Cnt
    public static int getSiliconRunLastMonth(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String siliconSN = preferences.getString(Constants.SILICON_SN,"");
        String ym = ymChanger();
        return preferences.getInt(siliconSN + "Run" + ym, 0);
    }
    public static int getSiliconCntLastMonth(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String siliconSN = preferences.getString(Constants.SILICON_SN,"");
        String ym = ymChanger();
        return preferences.getInt(siliconSN + "Cnt" + ym, 0);
    }

    // 이번달 사용량 가져오기
    // brush Run + Cnt
    public static int getBrushRunThisMonth(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String brushSN = preferences.getString(Constants.BRUSH_SN, "");
        String ym = ymFormat.format(currentTime);
        return preferences.getInt(brushSN + "Run" + ym, 0);
    }
    public static int getBrushCntThisMonth(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String brushSN = preferences.getString(Constants.BRUSH_SN, "");
        String ym = ymFormat.format(currentTime);
        return preferences.getInt(brushSN + "Cnt" + ym, 0);
    }

    // cooler Run + Cnt
    public static int getCoolerRunThisMonth(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String coolerSN = preferences.getString(Constants.COOLER_SN, "");
        String ym = ymFormat.format(currentTime);
        return preferences.getInt(coolerSN + "Run" + ym, 0);
    }
    public static int getCoolerCntThisMonth(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String coolerSN = preferences.getString(Constants.COOLER_SN, "");
        String ym = ymFormat.format(currentTime);
        return preferences.getInt(coolerSN + "Cnt" + ym, 0);
    }

    // puff Run + Cnt
    public static int getPuffRunThisMonth(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String puffSN = preferences.getString(Constants.PUFF_SN,"");
        String ym = ymFormat.format(currentTime);
        return preferences.getInt(puffSN + "Run" + ym, 0);
    }
    public static int getPuffCntThisMonth(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String puffSN = preferences.getString(Constants.PUFF_SN,"");
        String ym = ymFormat.format(currentTime);
        return preferences.getInt(puffSN + "Cnt" + ym, 0);
    }

    // silicon Run + Cnt
    public static int getSiliconRunThisMonth(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String siliconSN = preferences.getString(Constants.SILICON_SN,"");
        String ym = ymFormat.format(currentTime);
        return preferences.getInt(siliconSN + "Run" + ym, 0);
    }
    public static int getSiliconCntThisMonth(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        String siliconSN = preferences.getString(Constants.SILICON_SN,"");
        String ym = ymFormat.format(currentTime);
        return preferences.getInt(siliconSN + "Cnt" + ym, 0);
    }

    // 지난달 가져오기
    // 이번달이 1월인 경우 지난해 12월 가져오기
    private static String ymChanger() {
        String year = yFormat.format(currentTime);
        String month = mFormat.format(currentTime);
        int y = Integer.parseInt(year) - 1;
        int m = Integer.parseInt(month) - 1;
        String ym = null;
        if (month.equals("01")) {
            String yText = String.valueOf(y);
            ym = yText + "12";
        } else {
            String mText = String.valueOf(m);
            if (mText.length() == 1) {
                mText = "0" + mText;
                ym = year + mText;
            } else {
                ym = year + mText;
            }
        }
        return ym;
    }

    // 첫 접속시 연도 저장 [ 한번만 ]
    public static void setYear(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        int year = Integer.parseInt(yFormat.format(currentTime));
        editor.putInt(Constants.YEAR, year);
        editor.apply();
    }

    // 연도 가져오기
    public static int getYear(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        return preferences.getInt(Constants.YEAR, 2020);
    }
}
