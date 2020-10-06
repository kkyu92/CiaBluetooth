package com.example.ciabluetooth.fragment.pattern;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.ciabluetooth.Constants;
import com.example.ciabluetooth.R;
import com.example.ciabluetooth.databinding.FragmentPatternTimeBinding;
import com.example.ciabluetooth.fragment.BaseFragment;
import com.example.ciabluetooth.util.ChartBar;
import com.example.ciabluetooth.util.Common;
import com.example.ciabluetooth.util.CustomDialog;
import com.example.ciabluetooth.util.SharedPreferencesPackage;

import java.util.ArrayList;
import java.util.List;

public class PatternTimeFragment extends BaseFragment {
    private String TAG = this.getClass().getSimpleName();
    private FragmentPatternTimeBinding mBinding;
    private boolean isCreateView, isResume = false;
    private String time;

    private CustomDialog dialog;

    public static PatternTimeFragment getInstance(String time) {
        PatternTimeFragment patternTimeFragment = new PatternTimeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("time", time);
        patternTimeFragment.setArguments(bundle);
        return patternTimeFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        time = (String) bundle.getSerializable("time");
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_pattern_time, container, false);
        isCreateView = true;

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onCreate timeJSON:: " + time);
        setChart();
        if (isCreateView && isResume) {
            onFragmentResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        mBinding.chart.changeHeadImg.setOnClickListener(v -> show());
        mBinding.chart.changeHeadTxt.setOnClickListener(v -> show());
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        isResume = true;
        Log.e(TAG, "onFragmentResume timeJSON:: " + time);
        setChart();
    }

    private void setChart() {
        mBinding.setView(PatternTimeFragment.this);
        int brushRun = SharedPreferencesPackage.getBrushRunTotal(getContext());
        int coolerRun = SharedPreferencesPackage.getCoolerRunTotal(getContext());
        int puffRun = SharedPreferencesPackage.getPuffRunTotal(getContext());
        int siliconRun = SharedPreferencesPackage.getSiliconRunTotal(getContext());
        // 받는 값 초단위 || 헤드교체 시기 고정값
//        int brush = 446440, cooler = 126000, puff = 230400, silicon = 39600;
        Log.e("GET TOTAL", brushRun+"\n"+coolerRun+"\n"+puffRun+"\n"+siliconRun);
        new ChartBar(getContext(), mBinding.chart, brushRun, coolerRun, puffRun, siliconRun);
    }

    private void show() {
        dialog = new CustomDialog();
        dialog.setNoticeType(getActivity(), Constants.DIALOG_GRAPH);
//        dialog.setCancelable(false);
        dialog.show(getActivity().getSupportFragmentManager(), Constants.GRAPH_DIALOG);
    }

//    private Handler mHandler = new Handler(Looper.getMainLooper()) {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case Constants.MESSAGE_MAKE_GRAPH_TIME:
//                    setChart();
//                    break;
//            }
//        }
//    };
}
