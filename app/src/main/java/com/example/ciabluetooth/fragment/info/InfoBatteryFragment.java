package com.example.ciabluetooth.fragment.info;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.ciabluetooth.R;
import com.example.ciabluetooth.databinding.FragmentInfoBatteryBinding;
import com.example.ciabluetooth.fragment.BaseFragment;
import com.example.ciabluetooth.util.SharedPreferencesPackage;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class InfoBatteryFragment extends BaseFragment {
    private String TAG = this.getClass().getSimpleName();
    private FragmentInfoBatteryBinding mBinding;
    private boolean isCreateView, isResume = false;
    private int battery;

    public static InfoBatteryFragment getInstance(int battery) {
        InfoBatteryFragment infoBatteryFragment = new InfoBatteryFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("battery", battery);
        infoBatteryFragment.setArguments(bundle);
        return infoBatteryFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        battery = (int) bundle.getSerializable("battery");
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_info_battery, container, false);
        isCreateView = true;

        mBinding.deviceNum.setText(SharedPreferencesPackage.getDeviceID(getContext()));
        makeCircleBattery(battery);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onCreate battery:: " + battery);
        if (isCreateView && isResume) {
            onFragmentResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        Log.e(TAG, "onFragmentResume");
        isResume = true;
        Log.e(TAG, "onFragmentResume battery:: " + battery);
        if (mBinding != null && mBinding.getView() == null) {

        }
    }

    private void makeCircleBattery(int battery) {
        // 그래프 생성
// Set Progress
//        mBinding.circularProgressBar.setProgress(20f);
// or with animation
            mBinding.batteryProgress.setProgressWithAnimation(battery, 3000L); // =1s

// Set Progress Max
//            mBinding.circularProgressBar.setProgressMax(200f);

// Set ProgressBar Color
//            mBinding.circularProgressBar.setProgressBarColor(Color.BLACK);
// or with gradient
//            mBinding.circularProgressBar.setProgressBarColorStart(Color.GRAY);
//            mBinding.circularProgressBar.setProgressBarColorEnd(Color.RED);
//            mBinding.circularProgressBar.setProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);

// Set background ProgressBar Color
//            mBinding.circularProgressBar.setBackgroundProgressBarColor(Color.GRAY);
// or with gradient
//            mBinding.circularProgressBar.setBackgroundProgressBarColorStart(Color.WHITE);
//            mBinding.circularProgressBar.setBackgroundProgressBarColorEnd(Color.RED);
//            mBinding.circularProgressBar.setBackgroundProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);

// Set Width
//            mBinding.circularProgressBar.setProgressBarWidth(7f); // in DP
//            mBinding.circularProgressBar.setBackgroundProgressBarWidth(3f); // in DP

// Other
        mBinding.batteryProgress.setRoundBorder(true);
//        mBinding.circularProgressBar.setStartAngle(180f);
        mBinding.batteryProgress.setProgressDirection(CircularProgressBar.ProgressDirection.TO_RIGHT);
    }
}
