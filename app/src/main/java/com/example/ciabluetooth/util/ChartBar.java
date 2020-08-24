package com.example.ciabluetooth.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.example.ciabluetooth.R;
import com.example.ciabluetooth.databinding.BarChartBinding;


public class ChartBar {
    private Context mContext;

    private int brush;
    private int cooler;
    private int puff;
    private int silicon;
    private int changeHead;

    private BarChartBinding mBinding;

    public ChartBar(Context context, BarChartBinding binding, int brush, int cooler, int puff, int silicon, int changeHead) {
        mContext = context;
        this.brush = brush;
        this.cooler = cooler;
        this.puff = puff;
        this.silicon = silicon;
        this.changeHead = changeHead;

        mBinding = binding;

        new StartView().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void setBarView() {
        Log.i("AvgBarFragment", "setBarView: " + changeHead);
//        mBinding.unit.setText(R.string.time);
//        mBinding.hundred.setText(R.string.hundred);
//        mBinding.eighty.setText(R.string.eighty);
//        mBinding.sixty.setText(R.string.sixty);
//        mBinding.forty.setText(R.string.forty);
//        mBinding.twenty.setText(R.string.twenty);

        // 그래프 %
        int brushPer, coolerPer, puffPer, siliconPer;
        // 교체시기 = 고정 || percent 로 변환
        int changeHead = 120, changeHeadPer, max = 0;
        // 시간으로 변환
        brush = changeHour(brush);
        cooler = changeHour(cooler);
        puff = changeHour(puff);
        silicon = changeHour(silicon);
//        brush = 30;
//        cooler = 24;
//        puff = 17;
//        silicon = 8;

        // 100% 의 시간 || percent
        int hundred = changeHead * 100 / 80;
        brushPer = brush * 100 / hundred;
        coolerPer = cooler * 100 / hundred;
        puffPer = puff * 100 / hundred;
        siliconPer = silicon * 100 / hundred;
        changeHeadPer = changeHead * 100 / hundred;
        // setText hour
        mBinding.brushHour.setText(brush + "H");
        mBinding.coolerHour.setText(cooler + "H");
        mBinding.puffHour.setText(puff + "H");
        mBinding.siliconHour.setText(silicon + "H");

        // averageBar
        double height = (double) mBinding.averageBar.getHeight() / 100;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) mBinding.average.getLayoutParams();
        marginLayoutParams.topMargin = (int) (height * (100 - changeHeadPer) + Common.getAddLinear(mContext, changeHeadPer, 0.3f));
        mBinding.average.setLayoutParams(marginLayoutParams);

        // Question Number Bar
        ViewGroup.LayoutParams params = mBinding.brushBar.getLayoutParams();
        params.height = (int) (height * (brushPer) + Common.getAddLinear(mContext, brushPer, 0.3f));
        mBinding.brushBar.setLayoutParams(params);

        params = mBinding.coolerBar.getLayoutParams();
        params.height = (int) (height * (coolerPer) + Common.getAddLinear(mContext, coolerPer, 0.3f));
        mBinding.coolerBar.setLayoutParams(params);

        params = mBinding.puffBar.getLayoutParams();
        params.height = (int) (height * (puffPer) + Common.getAddLinear(mContext, puffPer, 0.3f));
        mBinding.puffBar.setLayoutParams(params);

        params = mBinding.siliconBar.getLayoutParams();
        params.height = (int) (height * (siliconPer) + Common.getAddLinear(mContext, siliconPer, 0.3f));
        mBinding.siliconBar.setLayoutParams(params);
    }

    @SuppressLint("StaticFieldLeak")
    private class StartView extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                mBinding.averageBar.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
            } catch (Exception e) {
                mBinding.averageBar.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
            }

            return null;
        }
    }

    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            setBarView();
            mBinding.averageBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    };


    private int changeHour(int time) {
        int hour, min, sec;

        min = time / 60;
        hour = min / 60;
        sec = time % 60;
        min = min % 60;
        return hour;
    }

    private int getMax(int brush, int cooler, int puff, int silicon) {
        int max;
        max = Math.max(brush, cooler);
        max = Math.max(max, puff);
        max = Math.max(max, silicon);
        return max;
    }
}