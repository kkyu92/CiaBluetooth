package com.withmind.ciabluetooth.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.withmind.ciabluetooth.SplashScreenActivity;

import java.util.Objects;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class BaseFragment extends Fragment {
    public static String INIT = "";
    public String TAG = "BaseFragment";

    public void onFragmentResume() {
        checkInternetState();
        if (INIT == null) {
            Intent intent = new Intent(getContext(), SplashScreenActivity.class);
            startActivity(intent);
            finishFragment();
        }
    }

    public void stopFragment() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    protected void finishFragment() {
        if (getFragmentManager() != null) {
            getFragmentManager().popBackStack();
        }
    }

    public void checkInternetState() {
        ConnectivityManager connectivityManager = (ConnectivityManager) Objects.requireNonNull(getContext()).getSystemService(CONNECTIVITY_SERVICE);
        if (!(connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected())) {
            new AlertDialog.Builder(getContext())
                    .setMessage("네트워크 연결상태를 확인해 주세요.")
                    .setCancelable(false)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.e("onClickFinish", "startActivity Intro start");
                            Intent finish = new Intent(getContext(), SplashScreenActivity.class);
                            finish.putExtra("TAG", "finish");
                            startActivity(finish);
                            Log.e("onClickFinish", "startActivity Intro end");
                        }
                    })
                    .show();
        }
    }

    // hour 계산
    public String changeHour(int headRun) {
        int day = headRun / (60 * 60 * 24);
        int hour = (headRun - day * 60 * 60 * 24) / (60 * 60);
        int min = (headRun - day * 60 * 60 * 24 - hour * 3600) / 60;
        int sec = headRun % 60;
        String changeHour;

        if (headRun >= 86400) {
            changeHour = day + "일";
        } else if (headRun >= 3600) {
            changeHour = hour + "시";
        } else if (headRun >= 60) {
            changeHour = min + "분";
        } else {
            changeHour = sec + "초";
        }
        return changeHour;

//        String changeHour = "0";
//        if (headRun <= 360) {
//            changeHour = "0.1";
//        } else if (headRun < 3600) {
//            double changeDouble = (double) headRun / 3600;
//            changeHour = String.format("%.1f", changeDouble);
//        } else if (headRun == 3600) {
//            changeHour = "1";
//        } else {
//            changeHour = String.valueOf(headRun / 3600);
//        }
//        Log.e(TAG, "headRun :: "+ headRun);
//        Log.e(TAG, "changeHour :: "+ changeHour);
//        return changeHour;
    }
}
