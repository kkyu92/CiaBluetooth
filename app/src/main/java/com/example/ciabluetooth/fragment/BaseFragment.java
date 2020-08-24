package com.example.ciabluetooth.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.ciabluetooth.SplashScreenActivity;

import java.util.Objects;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class BaseFragment extends Fragment {
    public static String INIT = "";
    public String TAG = "BaseFragment";

    public void onFragmentResume()
    {
        checkInternetState();
        if (INIT == null) {
            Intent intent = new Intent(getContext(), SplashScreenActivity.class);
            startActivity(intent);
            finishFragment();
        }
    }

    public void stopFragment()
    {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    protected void finishFragment() {
        if (getFragmentManager() != null)
        {
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
}
