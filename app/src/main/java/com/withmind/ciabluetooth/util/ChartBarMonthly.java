package com.withmind.ciabluetooth.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.withmind.ciabluetooth.databinding.BarChartMonthlyBinding;


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

        int lastAll = lastBrush + lastCooler + lastPuff + lastSilicon;
        if (lastAll == 0) { lastAll = 1;}
        int thisAll = thisBrush + thisCooler + thisPuff + thisSilicon;
        if (thisAll == 0) { thisAll = 1;}

        int lastBrushPer = (int) Math.round((double) (lastBrush * 100 / lastAll));
        int lastCoolerPer = (int) Math.round((double) lastCooler * 100 / lastAll);
        int lastPuffPer = (int) Math.round((double) lastPuff * 100 / lastAll);
        int lastSiliconPer = (int) Math.round((double) lastSilicon * 100 / lastAll);

        int thisBrushPer = (int) Math.round((double) thisBrush * 100 / thisAll);
        int thisCoolerPer = (int) Math.round((double) thisCooler * 100 / thisAll);
        int thisPuffPer = (int) Math.round((double) thisPuff * 100 / thisAll);
        int thisSiliconPer = (int) Math.round((double) thisSilicon * 100 / thisAll);

        // setContentDescription
        mBinding.lastBrushBar.setContentDescription("지난달 브러쉬는 " + lastBrushPer + "프로 사용하였습니다.");
        mBinding.lastCoolerBar.setContentDescription("지난달 쿨러는 " + lastCoolerPer + "프로 사용하였습니다.");
        mBinding.lastPuffBar.setContentDescription("지난달 퍼프는 " + lastPuffPer + "프로 사용하였습니다.");
        mBinding.lastSiliconBar.setContentDescription("지난달 실리콘는 " + lastSiliconPer + "프로 사용하였습니다.");

        // setText percent + contentDescription
        mBinding.brushPer.setText(thisBrushPer + "%");
//        mBinding.brushImg.setContentDescription("Brush " + thisBrushPer + "%");
        mBinding.thisBrushBar.setContentDescription("이번달 블러쉬는 " + thisBrushPer + "프로 사용하였습니다.");
        mBinding.coolerPer.setText(thisCoolerPer + "%");
        mBinding.thisCoolerBar.setContentDescription("이번달 쿨러는 " + thisCoolerPer + "프로 사용하였습니다.");
        mBinding.puffPer.setText(thisPuffPer + "%");
        mBinding.thisPuffBar.setContentDescription("이번달 퍼프는 " + thisPuffPer + "프로 사용하였습니다.");
        mBinding.siliconPer.setText(thisSiliconPer + "%");
        mBinding.thisSiliconBar.setContentDescription("이번달 실리콘는 " + thisSiliconPer + "프로 사용하였습니다.");

        if (lastBrushPer == 0) { lastBrushPer = 1; }
        if (lastCoolerPer == 0) { lastCoolerPer = 1; }
        if (lastPuffPer == 0) { lastPuffPer = 1; }
        if (lastSiliconPer == 0) { lastSiliconPer = 1; }
        if (thisBrushPer == 0) { thisBrushPer = 1; }
        if (thisCoolerPer == 0) { thisCoolerPer = 1; }
        if (thisPuffPer == 0) { thisPuffPer = 1; }
        if (thisSiliconPer == 0) { thisSiliconPer = 1; }

        // averageBar
        double height = (double) mBinding.averageBar.getHeight() / 100;

        // Question Number Bar
        ViewGroup.LayoutParams params = mBinding.lastBrushBar.getLayoutParams();
        params.height = (int) (height * (lastBrushPer) + Common.getAddLinear(mContext, lastBrushPer, 0.3f));
        mBinding.lastBrushBar.setLayoutParams(params);
        params = mBinding.thisBrushBar.getLayoutParams();
        params.height = (int) (height * (thisBrushPer) + Common.getAddLinear(mContext, thisBrushPer, 0.3f));
        mBinding.thisBrushBar.setLayoutParams(params);

        params = mBinding.lastCoolerBar.getLayoutParams();
        params.height = (int) (height * (lastCoolerPer) + Common.getAddLinear(mContext, lastCoolerPer, 0.3f));
        mBinding.lastCoolerBar.setLayoutParams(params);
        params = mBinding.thisCoolerBar.getLayoutParams();
        params.height = (int) (height * (thisCoolerPer) + Common.getAddLinear(mContext, thisCoolerPer, 0.3f));
        mBinding.thisCoolerBar.setLayoutParams(params);

        params = mBinding.lastPuffBar.getLayoutParams();
        params.height = (int) (height * (lastPuffPer) + Common.getAddLinear(mContext, lastPuffPer, 0.3f));
        mBinding.lastPuffBar.setLayoutParams(params);
        params = mBinding.thisPuffBar.getLayoutParams();
        params.height = (int) (height * (thisPuffPer) + Common.getAddLinear(mContext, thisPuffPer, 0.3f));
        mBinding.thisPuffBar.setLayoutParams(params);

        params = mBinding.lastSiliconBar.getLayoutParams();
        params.height = (int) (height * (lastSiliconPer) + Common.getAddLinear(mContext, lastSiliconPer, 0.3f));
        mBinding.lastSiliconBar.setLayoutParams(params);
        params = mBinding.thisSiliconBar.getLayoutParams();
        params.height = (int) (height * (thisSiliconPer) + Common.getAddLinear(mContext, thisSiliconPer, 0.3f));
        mBinding.thisSiliconBar.setLayoutParams(params);
    }

    @SuppressLint("StaticFieldLeak")
    private class StartView extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                mBinding.averageBar.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
            } catch (Exception e) {
                Log.e("treeObserver", ""+e);
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