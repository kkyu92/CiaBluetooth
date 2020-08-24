package com.example.ciabluetooth.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.example.ciabluetooth.R;
import com.example.ciabluetooth.databinding.ActivityMainBinding;
import com.example.ciabluetooth.util.Common;

public class MainActivity extends BaseActivity {
    private String TAG = this.getClass().getSimpleName();
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        mBinding.setView(MainActivity.this);

        // 값 받아와야함 [ bluetooth ]
        saveData(0, 0, 0, 0);
        // battery percent line TODO +10
        setData(80);
        // setText(battery) && 20이상부터 텍스트 보이게
        mBinding.percentText.setText(80+"%");
        mBinding.percentTextContainer.setVisibility(View.VISIBLE);
        // 끼워진 헤드 표시
        mBinding.brushPoint.setVisibility(View.VISIBLE);
        mBinding.coolerPoint.setVisibility(View.INVISIBLE);
        mBinding.puffPoint.setVisibility(View.INVISIBLE);
        mBinding.siliconPoint.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void startActivity(Class className) {
        Intent intent = new Intent(MainActivity.this, className);
        startActivity(intent);
    }

    private void headStartActivity(String name) {
        Intent brush = new Intent(MainActivity.this, HeadActivity.class);
        brush.putExtra("name", name);
        startActivity(brush);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.brush_container:
                headStartActivity("Brush");
                break;
            case R.id.cooler_container:
                headStartActivity("Cooler");
                break;
            case R.id.puff_container:
                headStartActivity("Puff");
                break;
            case R.id.silicon_container:
                headStartActivity("Silicon");
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
