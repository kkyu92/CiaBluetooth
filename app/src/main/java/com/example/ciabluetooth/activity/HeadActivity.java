package com.example.ciabluetooth.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.ciabluetooth.R;
import com.example.ciabluetooth.databinding.ActivityHeadBinding;
import com.example.ciabluetooth.util.Common;
import com.example.ciabluetooth.util.SharedPreferencesPackage;

public class HeadActivity extends BaseActivity {
    private String TAG = this.getClass().getSimpleName();
    private ActivityHeadBinding mBinding;
    private String headName;
    private int headRun, headCnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(HeadActivity.this, R.layout.activity_head);
        mBinding.setView(HeadActivity.this);

        Intent getIntent = getIntent();
        headName = getIntent.getStringExtra("name");
        headRun = getIntent.getIntExtra("headRun", 0);
        headCnt = getIntent.getIntExtra("headCnt", 0);
        allRun = getIntent.getIntExtra("allRun", 0);
        allCnt = getIntent.getIntExtra("allCnt", 0);
        // activity, guide title
        mBinding.title.setText(headName);
        mBinding.guideTitle.setText(headName+getString(R.string.guide_head));

        // TODO 헤드기기 교체 알림 기준

        // progress height 길이 조절
        mBinding.percentProgress.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int progressWidth = mBinding.percentProgress.getWidth();
                int progressHeight = mBinding.percentProgress.getHeight();
                Common.changeProgressView(progressWidth, progressHeight, mBinding.percentProgress);
                Common.changeProgressView(progressWidth, progressHeight, mBinding.totalProgress);
                Log.e(TAG, "progressSize ::: " + progressWidth + " || " + progressHeight);
                mBinding.percentProgress.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setGraph();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;
            case R.id.ad:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.hosiden.co.kr/"));
                startActivity(intent);
                break;
        }
    }

    private void setBarPercent(int allCnt, int allRun, int headCnt, int headRun) {
        int cntPer = headCnt * 100 / allCnt;
        int runPer = headRun * 100 / allRun;
        mBinding.percentProgress.setProgressWithAnimation(cntPer, 2000L); // =2 sec
        mBinding.percentText.setText(String.valueOf(cntPer));

        mBinding.totalProgress.setProgressWithAnimation(runPer, 2000L);
        // hour change
        mBinding.totalText.setText(changeHour(headRun));

        setBar(allCnt, headCnt, headCnt+"회");
    }

    // 그래프 그려주기
    private void setGraph() {
        // 전체 헤드 대비 사용율 % || 전체 헤드 대비 사용시간 % || 전체 헤드 대비 사용횟수 % || step 별 이미지, 설명 text 4ea
        setBarPercent(allCnt, allRun, headCnt, headRun);
        switch (headName) {
            case "Brush":
                mBinding.ad.setText("Brush 광고");
                mBinding.headNum.setText("   " + SharedPreferencesPackage.getBrushID(this));
                break;
            case "Cooler":
                // step img, text change
                mBinding.headImg.setBackgroundResource(R.drawable.img_head_cooler_big);
                mBinding.step1Img.setBackgroundResource(R.drawable.ic_img_cooler_step1);
                mBinding.step2Img.setBackgroundResource(R.drawable.img_brush_guide1);
                mBinding.step3Img.setBackgroundResource(R.drawable.img_brush_guide3);
                mBinding.step4Img.setBackgroundResource(R.drawable.ic_img_cooler_step4);
                setGuideText(getString(R.string.cooler_step1), getString(R.string.cooler_step2), getString(R.string.cooler_step3), getString(R.string.cooler_step4));
                mBinding.ad.setText("Cooler 광고");
                mBinding.headNum.setText("   " + SharedPreferencesPackage.getCoolerID(this));
                break;
            case "Puff":
                // step img, text change
                mBinding.headImg.setBackgroundResource(R.drawable.img_head_puff_big);
                mBinding.step1Img.setBackgroundResource(R.drawable.ic_img_cooler_step1);
                mBinding.step2Img.setBackgroundResource(R.drawable.img_brush_guide1);
                mBinding.step3Img.setBackgroundResource(R.drawable.img_brush_guide3);
                mBinding.step4Img.setBackgroundResource(R.drawable.ic_img_cooler_step1);
                setGuideText(getString(R.string.puff_step1), getString(R.string.puff_step2), getString(R.string.puff_step3), getString(R.string.puff_step4));
                mBinding.ad.setText("Puff 광고");
                mBinding.headNum.setText("   " + SharedPreferencesPackage.getPuffID(this));
                break;
            case "Silicon":
                // step img, text change
                mBinding.headImg.setBackgroundResource(R.drawable.img_head_silicon_big);
                mBinding.step1Img.setBackgroundResource(R.drawable.img_brush_guide1);
                mBinding.step2Img.setBackgroundResource(R.drawable.img_brush_guide2);
                mBinding.step3Img.setBackgroundResource(R.drawable.img_silicon_guide3);
                mBinding.step4Img.setBackgroundResource(R.drawable.img_brush_guide4);
                setGuideText(getString(R.string.silicon_step1), getString(R.string.silicon_step2), getString(R.string.silicon_step3), getString(R.string.silicon_step4));
                mBinding.ad.setText("Silicon 광고");
                mBinding.headNum.setText("   " + SharedPreferencesPackage.getSiliconID(this));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    // guide setting
    private void setGuideText(String step1Txt, String step2Txt, String step3Txt, String step4Txt) {
        mBinding.step1Contents.setText(step1Txt);
        mBinding.step2Contents.setText(step2Txt);
        mBinding.step3Contents.setText(step3Txt);
        mBinding.step4Contents.setText(step4Txt);
    }

    // percent line
    private void setBar(int total, int head, String countTxt) {
        // 전체 256 기준 133  bar setting
        int count = head * 100 / total;
        mBinding.totalCount.setText(String.valueOf(total));
        mBinding.useCount.setText(countTxt);
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
