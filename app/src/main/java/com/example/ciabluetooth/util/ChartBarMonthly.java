package com.example.ciabluetooth.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.example.ciabluetooth.R;
import com.example.ciabluetooth.databinding.BarChartMonthlyBinding;


public class ChartBarMonthly {
    private Context mContext;

    private int lastBrush, thisBrush;
    private int lastCooler, thisCooler;
    private int lastPuff, thisPuff;
    private int lastSilicon, thisSilicon;
//    private int fifth;
//    private String unit;
//    private int average;

    private BarChartMonthlyBinding mBinding;

    public ChartBarMonthly(Context context, BarChartMonthlyBinding binding, int lastBrush, int thisBrush, int lastCooler, int thisCooler, int lastPuff, int thisPuff, int lastSilicon, int thisSilicon) {
        mContext = context;
        this.lastBrush = lastBrush;
        this.thisBrush = thisBrush;
        this.lastCooler = lastCooler;
        this.thisCooler = thisCooler;
        this.lastPuff = lastPuff;
        this.thisPuff = thisPuff;
        this.lastSilicon = lastSilicon;
        this.thisSilicon = thisSilicon;
//        this.unit = unit;
//        this.average = average;
//        this.fifth = fifth;

        mBinding = binding;

        new StartView().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void setBarView() {
//        mBinding.unit.setText(R.string.avg);
//        mBinding.hundred.setText(R.string.hundred);
//        mBinding.eighty.setText(R.string.eighty);
//        mBinding.sixty.setText(R.string.sixty);
//        mBinding.forty.setText(R.string.forty);
//        mBinding.twenty.setText(R.string.twenty);

        // setText percent
        mBinding.brushPer.setText(lastBrush + "%");
        mBinding.coolerPer.setText(lastCooler + "%");
        mBinding.puffPer.setText(lastPuff + "%");
        mBinding.siliconPer.setText(lastSilicon + "%");

        // averageBar
        double height = (double) mBinding.averageBar.getHeight() / 100;

        // Question Number Bar
        ViewGroup.LayoutParams params = mBinding.lastBrushBar.getLayoutParams();
        params.height = (int) (height * (lastBrush) + Common.getAddLinear(mContext, lastBrush, 0.3f));
        mBinding.lastBrushBar.setLayoutParams(params);
        params = mBinding.thisBrushBar.getLayoutParams();
        params.height = (int) (height * (thisBrush) + Common.getAddLinear(mContext, thisBrush, 0.3f));
        mBinding.thisBrushBar.setLayoutParams(params);

        params = mBinding.lastCoolerBar.getLayoutParams();
        params.height = (int) (height * (lastCooler) + Common.getAddLinear(mContext, lastCooler, 0.3f));
        mBinding.lastCoolerBar.setLayoutParams(params);
        params = mBinding.thisCoolerBar.getLayoutParams();
        params.height = (int) (height * (thisCooler) + Common.getAddLinear(mContext, thisCooler, 0.3f));
        mBinding.thisCoolerBar.setLayoutParams(params);

        params = mBinding.lastPuffBar.getLayoutParams();
        params.height = (int) (height * (lastPuff) + Common.getAddLinear(mContext, lastPuff, 0.3f));
        mBinding.lastPuffBar.setLayoutParams(params);
        params = mBinding.thisPuffBar.getLayoutParams();
        params.height = (int) (height * (thisPuff) + Common.getAddLinear(mContext, thisPuff, 0.3f));
        mBinding.thisPuffBar.setLayoutParams(params);

        params = mBinding.lastSiliconBar.getLayoutParams();
        params.height = (int) (height * (lastSilicon) + Common.getAddLinear(mContext, lastSilicon, 0.3f));
        mBinding.lastSiliconBar.setLayoutParams(params);
        params = mBinding.thisSiliconBar.getLayoutParams();
        params.height = (int) (height * (thisSilicon) + Common.getAddLinear(mContext, thisSilicon, 0.3f));
        mBinding.thisSiliconBar.setLayoutParams(params);
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
}