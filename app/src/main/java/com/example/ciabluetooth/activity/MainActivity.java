package com.example.ciabluetooth.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.example.ciabluetooth.R;
import com.example.ciabluetooth.databinding.ActivityMainBinding;
import com.example.ciabluetooth.util.Common;
import com.example.ciabluetooth.util.SharedPreferencesPackage;

public class MainActivity extends BaseActivity {
    private String TAG = this.getClass().getSimpleName();
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        mBinding.setView(MainActivity.this);

        brushRun = SharedPreferencesPackage.getBrushRunTotal(this);
        brushCnt = SharedPreferencesPackage.getBrushCntTotal(this);
        coolerRun = SharedPreferencesPackage.getCoolerRunTotal(this);
        coolerCnt = SharedPreferencesPackage.getCoolerCntTotal(this);
        puffRun = SharedPreferencesPackage.getPuffRunTotal(this);
        puffCnt = SharedPreferencesPackage.getPuffCntTotal(this);
        siliconRun = SharedPreferencesPackage.getSiliconRunTotal(this);
        siliconCnt = SharedPreferencesPackage.getSiliconCntTotal(this);
        head = SharedPreferencesPackage.getHeadType(this);
        battery = SharedPreferencesPackage.getDeviceBattery(this);
        allRun = brushRun + coolerRun + puffRun + siliconRun;
        allCnt = brushCnt + coolerCnt + puffCnt + siliconCnt;
        Log.e(TAG, brushCnt+"\n"+coolerCnt+"\n"+puffCnt+"\n"+siliconCnt);

        mBinding.cardBrushCount.setText(String.valueOf(brushCnt));
        mBinding.cardCoolerCount.setText(String.valueOf(coolerCnt));
        mBinding.cardPuffCount.setText(String.valueOf(puffCnt));
        mBinding.cardSiliconCount.setText(String.valueOf(siliconCnt));

        setData(battery);
        // setText(battery) && 20이상부터 텍스트 보이게
        if (battery >= 20) {
            mBinding.percentText.setText(battery + getString(R.string.per));
            mBinding.percentTextContainer.setVisibility(View.VISIBLE);
        }
        // 끼워진 헤드 표시
        switch (head) {
            case 0:
                mBinding.brushPoint.setVisibility(View.INVISIBLE);
                mBinding.coolerPoint.setVisibility(View.INVISIBLE);
                mBinding.puffPoint.setVisibility(View.INVISIBLE);
                mBinding.siliconPoint.setVisibility(View.INVISIBLE);
                break;
            case 1:
                mBinding.brushPoint.setVisibility(View.VISIBLE);
                mBinding.coolerPoint.setVisibility(View.INVISIBLE);
                mBinding.puffPoint.setVisibility(View.INVISIBLE);
                mBinding.siliconPoint.setVisibility(View.INVISIBLE);
                break;
            case 2:
                mBinding.brushPoint.setVisibility(View.INVISIBLE);
                mBinding.coolerPoint.setVisibility(View.VISIBLE);
                mBinding.puffPoint.setVisibility(View.INVISIBLE);
                mBinding.siliconPoint.setVisibility(View.INVISIBLE);
                break;
            case 3:
                mBinding.brushPoint.setVisibility(View.INVISIBLE);
                mBinding.coolerPoint.setVisibility(View.INVISIBLE);
                mBinding.puffPoint.setVisibility(View.VISIBLE);
                mBinding.siliconPoint.setVisibility(View.INVISIBLE);
                break;
            case 4:
                mBinding.brushPoint.setVisibility(View.INVISIBLE);
                mBinding.coolerPoint.setVisibility(View.INVISIBLE);
                mBinding.puffPoint.setVisibility(View.INVISIBLE);
                mBinding.siliconPoint.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void startActivity(Class className) {
        Intent intent = new Intent(MainActivity.this, className);
        startActivity(intent);
    }

    private void headStartActivity(String name, int headRun, int headCnt) {
        Intent head = new Intent(MainActivity.this, HeadActivity.class);
        head.putExtra("name", name);
        head.putExtra("headRun", headRun);
        head.putExtra("headCnt", headCnt);
        head.putExtra("allRun", allRun);
        head.putExtra("allCnt", allCnt);
        startActivity(head);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.brush_container:
                headStartActivity("Brush", brushRun, brushCnt);
                break;
            case R.id.cooler_container:
                headStartActivity("Cooler", coolerRun, coolerCnt);
                break;
            case R.id.puff_container:
                headStartActivity("Puff", puffRun, puffCnt);
                break;
            case R.id.silicon_container:
                headStartActivity("Silicon", siliconRun, siliconCnt);
                break;
            case R.id.info_btn:
                startActivity(CiaInfoActivity.class);
                break;
            case R.id.manual_btn:
                startActivity(ManualActivity.class);
                break;
            case R.id.pattern_btn:
                startActivity(PatternActivity.class);
                break;
        }
    }

    // percent line
    private void setData(int count) {
        new StartBar().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, count);
    }

    private class StartBar extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... integers) {
            Common.setPercentGaugeWidth(mBinding.line, mBinding.percentLine, integers[0]);
            return null;
        }
    }
}
