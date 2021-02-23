package com.withmind.ciabluetooth.fragment.pattern;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.withmind.ciabluetooth.R;
import com.withmind.ciabluetooth.databinding.FragmentPatternMonthlyBinding;
import com.withmind.ciabluetooth.fragment.BaseFragment;
import com.withmind.ciabluetooth.util.ChartBarMonthly;
import com.withmind.ciabluetooth.util.SharedPreferencesPackage;

public class PatternMonthlyFragment extends BaseFragment {
    private String TAG = this.getClass().getSimpleName();
    private FragmentPatternMonthlyBinding mBinding;
    private boolean isCreateView, isResume = false;
    private String getStringData;

    public static PatternMonthlyFragment getInstance(String getData) {
        PatternMonthlyFragment patternMonthlyFragment = new PatternMonthlyFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("getStringData", getData);
        patternMonthlyFragment.setArguments(bundle);
        return patternMonthlyFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        getStringData = (String) bundle.getSerializable("getStringData");
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_pattern_monthly, container, false);
        isCreateView = true;
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setChart();
        if (isCreateView && isResume) {
            onFragmentResume();
        }
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        isResume = true;
        setChart();
    }

    private void setChart() {
        mBinding.setView(PatternMonthlyFragment.this);
        int lastBrush = SharedPreferencesPackage.getBrushCntLastMonth(getContext());
        int thisBrush = SharedPreferencesPackage.getBrushCntThisMonth(getContext());
        int lastCooler = SharedPreferencesPackage.getCoolerCntLastMonth(getContext());
        int thisCooler = SharedPreferencesPackage.getCoolerCntThisMonth(getContext());
        int lastPuff = SharedPreferencesPackage.getPuffCntLastMonth(getContext());
        int thisPuff = SharedPreferencesPackage.getPuffCntThisMonth(getContext());
        int lastSilicon = SharedPreferencesPackage.getSiliconCntLastMonth(getContext());
        int thisSilicon = SharedPreferencesPackage.getSiliconCntThisMonth(getContext());
        new ChartBarMonthly(getContext(), mBinding.chart, lastBrush, thisBrush, lastCooler, thisCooler, lastPuff, thisPuff, lastSilicon, thisSilicon);
    }
}
