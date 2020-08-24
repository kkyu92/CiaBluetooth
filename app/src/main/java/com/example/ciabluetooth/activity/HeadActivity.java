package com.example.ciabluetooth.activity;

import android.content.Intent;
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

public class HeadActivity extends BaseActivity {
    private String TAG = this.getClass().getSimpleName();
    private ActivityHeadBinding mBinding;
    private String headName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(HeadActivity.this, R.layout.activity_head);
        mBinding.setView(HeadActivity.this);

        Intent getIntent = getIntent();
        headName = getIntent.getStringExtra("name");
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
        setGraph(100, 100, 100);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;
            case R.id.ad:
                Toast.makeText(this, "광고", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // 그래프 그려주기
    private void setGraph(int usePercent, int useTimePercent, int useCountPercent) {
        // 전체 헤드 대비 사용율 % || 전체 헤드 대비 사용시간 % || 전체 헤드 대비 사용횟수 % || step 별 이미지, 설명 text 4ea
        switch (headName) {
            case "Brush":
                // [ 사용율 ] 기준이 뭐냐
                mBinding.percentProgress.setProgressWithAnimation(45, 2000L); // =3 sec
                mBinding.percentText.setText("45");

                // 전체 사용시간 248 || brush 헤드의 사용시간 [ 사용시간 ]
//TODO                int time = brushTime * 100 / allTime;
                mBinding.totalProgress.setProgressWithAnimation(50, 2000L);
                mBinding.totalText.setText("124");

                // 전체 256 기준 133 [ 사용횟수 ]
                setBar(256, 133, "133회");
                break;
            case "Cooler":
                mBinding.percentProgress.setProgressWithAnimation(10, 2000L);
                mBinding.percentText.setText("10");
                mBinding.totalProgress.setProgressWithAnimation(14, 2000L);
//                14 * 248 / 100
                mBinding.totalText.setText("35");
                // 전체 256 기준 23
                setBar(256, 23, "23회");

                // step img, text change
                mBinding.headImg.setBackgroundResource(R.drawable.img_head_cooler_big);
                mBinding.step1Img.setBackgroundResource(R.drawable.ic_img_cooler_step1);
                mBinding.step2Img.setBackgroundResource(R.drawable.img_brush_guide1);
                mBinding.step3Img.setBackgroundResource(R.drawable.img_brush_guide3);
                mBinding.step4Img.setBackgroundResource(R.drawable.ic_img_cooler_step4);
                setGuideText(getString(R.string.cooler_step1), getString(R.string.cooler_step2), getString(R.string.cooler_step3), getString(R.string.cooler_step4));
                break;
            case "Puff":
                mBinding.percentProgress.setProgressWithAnimation(30, 2000L);
                mBinding.percentText.setText("30");
                mBinding.totalProgress.setProgressWithAnimation(12, 2000L);
                mBinding.totalText.setText("30");
                setBar(256, 41, "41회");

                // step img, text change
                mBinding.headImg.setBackgroundResource(R.drawable.img_head_puff_big);
                mBinding.step1Img.setBackgroundResource(R.drawable.ic_img_cooler_step1);
                mBinding.step2Img.setBackgroundResource(R.drawable.img_brush_guide1);
                mBinding.step3Img.setBackgroundResource(R.drawable.img_brush_guide3);
                mBinding.step4Img.setBackgroundResource(R.drawable.ic_img_cooler_step1);
                setGuideText(getString(R.string.puff_step1), getString(R.string.puff_step2), getString(R.string.puff_step3), getString(R.string.puff_step4));
                break;
            case "Silicon":
                mBinding.percentProgress.setProgressWithAnimation(15, 2000L);
                mBinding.percentText.setText("15");
                mBinding.totalProgress.setProgressWithAnimation(24, 2000L);
                mBinding.totalText.setText("59");
                setBar(256, 59, "59회");

                // step img, text change
                mBinding.headImg.setBackgroundResource(R.drawable.img_head_silicon_big);
                mBinding.step1Img.setBackgroundResource(R.drawable.img_brush_guide1);
                mBinding.step2Img.setBackgroundResource(R.drawable.img_brush_guide2);
                mBinding.step3Img.setBackgroundResource(R.drawable.img_silicon_guide3);
                mBinding.step4Img.setBackgroundResource(R.drawable.img_brush_guide4);
                setGuideText(getString(R.string.silicon_step1), getString(R.string.silicon_step2), getString(R.string.silicon_step3), getString(R.string.silicon_step4));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    // per 계산
    private void calculateData() {

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
