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

        editor.putInt("brushSNRun202006", 300);
        editor.putInt("brushSNCnt202007", 300);
        editor.putInt("brushSNRun202006", 300);
        editor.putInt("brushSNCnt202007", 300);

        editor.putInt("coolerSNRun202006", 300);
        editor.putInt("coolerSNCnt202007", 300);
        editor.putInt("coolerSNRun202006", 300);
        editor.putInt("coolerSNCnt202007", 300);

        editor.putInt("puffSNRun202006", 300);
        editor.putInt("puffSNCnt202007", 300);
        editor.putInt("puffSNRun202006", 300);
        editor.putInt("puffSNCnt202007", 300);

        editor.putInt("siliconSNRun202006", 300);
        editor.putInt("siliconSNRun202007", 300);
        editor.putInt("siliconSNCnt202006", 300);
        editor.putInt("siliconSNCnt202007", 300);
        editor.apply();
    }

    // 월별 사용량 저장
    // year + month (ex. 202007) 를 key 뒤에 붙여 월별 저장 사용
    // TODO: brush / cooler / puff / silicon
    public static void setAllData(Context context, String deviceSN, int deviceFual, int deviceRun, int deviceCnt, int headType, String brushSN, int brushRun, int brushCnt, String coolerSN, int coolerRun, int coolerCnt, String puffSN, int puffRun, int puffCnt, String siliconSN, int siliconRun, int siliconCnt) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String month = mFormat.format(currentTime);
        String year = yFormat.format(currentTime);
        // fake data
        //TODO: month => (String + 월)로 key 값 구분 1 ~ 12 값이 있다면 저장
        brushSN = "brushSN";
        coolerSN = "coolerSN";
        puffSN = "puffSN";
        siliconSN = "siliconSN";

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
        editor.putInt(deviceSN + "Run", deviceRun);
        editor.putInt(deviceSN + "Cnt", deviceCnt);
        editor.putInt(deviceSN + "HeadType", headType);
        editor.putString(Constants.BRUSH_SN, brushSN);
        editor.putInt(brushSN + "RunTotal", brushRun);
        editor.putInt(brushSN + "Run" + year + month, brushRun - brushRunTM);
        editor.putInt(brushSN + "CntTotal", brushCnt);
        editor.putInt(brushSN + "Cnt" + year + month, brushCnt - brushCntTM);
        editor.putString(Constants.COOLER_SN, coolerSN);
        editor.putInt(coolerSN + "RunTotal", coolerRun);
        editor.putInt(coolerSN + "Run" + year + month, coolerRun - coolerRunTM);
        editor.putInt(coolerSN + "CntTotal", coolerCnt);
        editor.putInt(coolerSN + "Cnt" + year + month, coolerCnt - coolerCntTM);
        editor.putString(Constants.PUFF_SN, puffSN);
        editor.putInt(puffSN + "RunTotal", puffRun);
        editor.putInt(puffSN + "Run" + year + month, puffRun - puffRunTM);
        editor.putInt(puffSN + "CntTotal", puffCnt);
        editor.putInt(puffSN + "Cnt" + year + month, puffCnt - puffCntTM);
        editor.putString(Constants.SILICON_SN, siliconSN);
        editor.putInt(siliconSN + "RunTotal", siliconRun);
        editor.putInt(siliconSN + "Run" + year + month, siliconRun - siliconRunTM);
        editor.putInt(siliconSN + "CntTotal", siliconCnt);
        editor.putInt(siliconSN + "Cnt" + year + month, siliconCnt - siliconCntTM);
        editor.apply();
    }

    // get deviceInfo
    public static String getDeviceID(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("base", Context.MODE_PRIVATE);
        return preferences.getString(Constants.DEVICE_SN, "");
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
