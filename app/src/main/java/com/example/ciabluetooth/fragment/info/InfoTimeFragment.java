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
import com.example.ciabluetooth.databinding.FragmentInfoTimeBinding;
import com.example.ciabluetooth.fragment.BaseFragment;
import com.example.ciabluetooth.util.SharedPreferencesPackage;

public class InfoTimeFragment extends BaseFragment {
    private String TAG = this.getClass().getSimpleName();
    private FragmentInfoTimeBinding mBinding;
    private boolean isCreateView, isResume = false;

    public static InfoTimeFragment getInstance() {
        return new InfoTimeFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_info_time, container, false);
        isCreateView = true;

        mBinding.deviceNum.setText(SharedPreferencesPackage.getDeviceID(getContext()));
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreated");
        if (isCreateView && isResume) {
            onFragmentResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        int deviceTime = SharedPreferencesPackage.getDeviceRun(getContext());
        int brushTotal = SharedPreferencesPackage.getBrushRunTotal(getContext());
        int coolerTotal = SharedPreferencesPackage.getCoolerRunTotal(getContext());
        int puffTotal = SharedPreferencesPackage.getPuffRunTotal(getContext());
        int siliconTotal = SharedPreferencesPackage.getSiliconRunTotal(getContext());
        mBinding.blockUsageTimeTxt.setText(changeHour(deviceTime));
        mBinding.brushTime.setText(changeHour(brushTotal));
        mBinding.coolerTime.setText(changeHour(coolerTotal));
        mBinding.puffTime.setText(changeHour(puffTotal));
        mBinding.siliconTime.setText(changeHour(siliconTotal));

//        mBinding.blockUsageTimeTxt.setText(changeHour(1000000));
//        mBinding.brushTime.setText(changeHour(75300));
//        mBinding.coolerTime.setText(changeHour(59889));
//        mBinding.puffTime.setText(changeHour(3500));
//        mBinding.siliconTime.setText(changeHour(59));
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        Log.e(TAG, "onFragmentResume");
        isResume = true;
        if (mBinding != null && mBinding.getView() == null) {

        }
    }
}
