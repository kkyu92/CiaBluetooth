package com.withmind.ciabluetooth.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.withmind.ciabluetooth.databinding.BarChartBinding;


public class ChartBar {
    private Context mContext;

    private int brush;
    private int cooler;
    private int puff;
    private int silicon;

    private BarChartBinding mBinding;

    public ChartBar(Context context, BarChartBinding binding, int brush, int cooler, int puff, int silicon) {
        mContext = context;
        this.brush = brush;
        this.cooler = cooler;
        this.puff = puff;
        this.silicon = silicon;

        mBinding = binding;

        new StartView().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void setBarView() {
        int highest = Math.max(brush, cooler);
        highest = Math.max(highest, puff);
        highest = Math.max(highest, silicon);

        int hundred = highest * 100 / 80;
        hundred = hundred == 0 ? 1 : hundred;
        int brushPer = (int) Math.round((double) (brush * 100 / hundred));
        int coolerPer = (int) Math.round((double) cooler * 100 / hundred);
        int puffPer = (int) Math.round((double) puff * 100 / hundred);
        int siliconPer = (int) Math.round((double) silicon * 100 / hundred);
//        int changeCoolerPer = highest * 100 / hundred;
//        mBinding.unit.setText(R.string.time);
//        mBinding.hundred.setText(R.string.hundred);
//        mBinding.eighty.setText(R.string.eighty);
//        mBinding.sixty.setText(R.string.sixty);
//        mBinding.forty.setText(R.string.forty);
//        mBinding.twenty.setText(R.string.twenty);

        // 교체시기 = 고정 || percent 로 변환
        int changeHead = 120, changeHeadPer, max = 0;
//        brush = 30;
//        cooler = 24;
//        puff = 17;
//        silicon = 8;

        // 100% 의 시간 || percent
//        brushPer = brush * 100 / hundred;
//        coolerPer = cooler * 100 / hundred;
//        puffPer = puff * 100 / hundred;
//        siliconPer = silicon * 100 / hundred;
//        changeHeadPer = changeHead * 100 / hundred;

//        mBinding.brushHour.setText(changeHour(75300));
//        mBinding.coolerHour.setText(changeHour(59889));
//        mBinding.puffHour.setText(changeHour(3500));
//        mBinding.siliconHour.setText(changeHour(59));
        // setText hour
        mBinding.brushHour.setText(changeHour(brush));
        mBinding.brushBar.setContentDescription("Brush 총 사용시간은 " + changeHour(brush) + "입니다.");
        mBinding.coolerHour.setText(changeHour(cooler));
        mBinding.coolerBar.setContentDescription("Cooler 총 사용시간은 " + changeHour(cooler) + "입니다.");
        mBinding.puffHour.setText(changeHour(puff));
        mBinding.puffBar.setContentDescription("Puff 총 사용시간은 " + changeHour(puff) + "입니다.");
        mBinding.siliconHour.setText(changeHour(silicon));
        mBinding.siliconBar.setContentDescription("silicon 총 사용시간은 " + changeHour(silicon) + "입니다.");
        Log.e("per", brushPer + "\n" + coolerPer + "\n" + puffPer + "\n" + siliconPer + "\n" + hundred);
        // averageBar
        double height = (double) mBinding.averageBar.getHeight() / 100;
//        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) mBinding.average.getLayoutParams();
//        marginLayoutParams.topMargin = (int) (height * (100 - changeCoolerPer) + Common.getAddLinear(mContext, changeCoolerPer, 0.3f));
//        mBinding.average.setLayoutParams(marginLayoutParams);

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

    // hour 계산
    private String changeHour(int headRun) {
        int day = headRun / (60 * 60 * 24);
        int hour = (headRun - day * 60 * 60 * 24) / (60 * 60);
        int min = (headRun - day * 60 * 60 * 24 - hour * 3600) / 60;
        int sec = headRun % 60;
        String changeHour;

        if (headRun >= 86400) {
            changeHour = day + "일";
        } else if (headRun >= 3600) {
            changeHour = hour + "시";
        } else if (headRun >= 60) {
            changeHour = min + "분";
        } else {
            changeHour = sec + "초";
        }
        return changeHour;
//        String changeHour = "0";
//        if (headRun == 0) {
//            changeHour = "0";
//        } else if (headRun <= 360) {
//            changeHour = "0.1";
//        } else if (headRun < 3600) {
//            double changeDouble = (double) headRun / 3600;
//            changeHour = String.format("%.1f", changeDouble);
//        } else if (headRun == 3600) {
//            changeHour = "1";
//        } else {
//            changeHour = String.format("%.1f", (double) headRun / 3600);
//        }
//        return changeHour;
    }

    private int getMax(int brush, int cooler, int puff, int silicon) {
        int max;
        max = Math.max(brush, cooler);
        max = Math.max(max, puff);
        max = Math.max(max, silicon);
        return max;
    }
}