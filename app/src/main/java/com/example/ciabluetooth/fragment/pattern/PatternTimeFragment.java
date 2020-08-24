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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.ciabluetooth.Constants;
import com.example.ciabluetooth.R;
import com.example.ciabluetooth.databinding.FragmentPatternTimeBinding;
import com.example.ciabluetooth.fragment.BaseFragment;
import com.example.ciabluetooth.util.ChartBar;
import com.example.ciabluetooth.util.Common;

import java.util.ArrayList;
import java.util.List;

public class PatternTimeFragment extends BaseFragment {
    private String TAG = this.getClass().getSimpleName();
    private FragmentPatternTimeBinding mBinding;
    private boolean isCreateView, isResume = false;
    private String time;

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
        // 받는 값 초단위 || 헤드교체 시기 고정값
        int brush = 446440, cooler = 126000, puff = 230400, silicon = 39600;
        int changeHead = 120;
        
        if (brush == 0 && cooler == 0 && puff == 0 && silicon == 0) {
            new ChartBar(getContext(), mBinding.chart, 20, 20, 20, 20, changeHead);
        } else {
            new ChartBar(getContext(), mBinding.chart, brush, cooler, puff, silicon, changeHead);
        }
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
