package com.withmind.ciabluetooth.fragment.info;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.withmind.ciabluetooth.R;
import com.withmind.ciabluetooth.databinding.FragmentInfoDeviceBinding;
import com.withmind.ciabluetooth.fragment.BaseFragment;
import com.withmind.ciabluetooth.util.SharedPreferencesPackage;

public class InfoDeviceFragment extends BaseFragment {
    private String TAG = this.getClass().getSimpleName();
    private FragmentInfoDeviceBinding mBinding;
    private boolean isCreateView, isResume = false;

    public static InfoDeviceFragment getInstance() {
        return new InfoDeviceFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_info_device, container, false);
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
