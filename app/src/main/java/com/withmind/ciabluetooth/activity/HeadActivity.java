package com.withmind.ciabluetooth.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.withmind.ciabluetooth.R;
import com.withmind.ciabluetooth.adapter.AdViewPagerAdapter;
import com.withmind.ciabluetooth.databinding.ActivityHeadBinding;
import com.withmind.ciabluetooth.util.Common;
import com.withmind.ciabluetooth.util.SharedPreferencesPackage;

public class HeadActivity extends BaseActivity {
    private String TAG = this.getClass().getSimpleName();
    private ActivityHeadBinding mBinding;
    private String headName, fffff;
    private int headRun, headCnt;
    private AdViewPagerAdapter adapter;
    private ViewPager viewPager;

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

        if (headName.equals("Brush")) {
            fffff = "브러시";
        } else if (headName.equals("Cooler")) {
            fffff = "쿨러";
        } else if (headName.equals("Puff")) {
            fffff = "퍼프";
        } else if (headName.equals("Silicone")) {
            fffff = "실리콘";
        } else {
            fffff = "ffffffffffffffff";
        }
        mBinding.headUse.setText(fffff + " 사용율");
        mBinding.guideTitle.setText(fffff + getString(R.string.guide_head));

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

        // ad viewpager
        viewPager = findViewById(R.id.ad);
        adapter = new AdViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
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
//        allCnt = allCnt == 0 ? 1 : allCnt;
//        allRun = allRun == 0 ? 1 : allRun;
        int cntPer, runPer;
        if (allCnt == 0 || allRun == 0) {
            cntPer = 0;
            runPer = 0;
        } else {
            cntPer = headCnt * 100 / allCnt;
            runPer = headRun * 100 / allRun;
        }
        mBinding.percentProgress.setProgressWithAnimation(cntPer, 2000L); // =2 sec
        mBinding.percentText.setText(String.valueOf(cntPer));
        mBinding.percentText.setContentDescription(fffff + " 사용율은 전체해드 대비 " + cntPer + "퍼센트 입니다");
        mBinding.totalProgress.setProgressWithAnimation(runPer, 2000L);
        // hour change
        String getTime = changeHour(headRun);
        String time = getTime.substring(0, getTime.length() - 1);
        String unit = getTime.substring(getTime.length() - 1);
        mBinding.totalText.setText(time);
        mBinding.totalText.setContentDescription(fffff + "사용시간은 " + time + unit + "입니다.");
        mBinding.totalUnit.setText(unit);

        // 교체시기
        int timeNum = Integer.parseInt(time);
        if (timeNum >= 365 && headName.equals("Cooler")) {
            mBinding.headChangeNotify.setVisibility(View.VISIBLE);
        } else if (timeNum >= 180 && (headName.equals("Brush"))) {
            mBinding.headChangeNotify.setVisibility(View.VISIBLE);
        } else if (timeNum >= 90 && (headName.equals("Puff") || headName.equals("Silicone"))) {
            mBinding.headChangeNotify.setVisibility(View.VISIBLE);
        } else {
            mBinding.headChangeNotify.setVisibility(View.INVISIBLE);
        }
        setBar(allCnt, headCnt, headCnt + "회");
    }

    // 그래프 그려주기
    private void setGraph() {
        // 전체 헤드 대비 사용율 % || 전체 헤드 대비 사용시간 % || 전체 헤드 대비 사용횟수 % || step 별 이미지, 설명 text 4ea
        setBarPercent(allCnt, allRun, headCnt, headRun);
        switch (headName) {
            case "Brush":
                mBinding.headNum.setText("   " + SharedPreferencesPackage.getBrushID(this));
                break;
            case "Cooler":
                // step img, text change
                mBinding.headImg.setBackgroundResource(R.drawable.img_head_cooler_big);
                mBinding.step1Img.setBackgroundResource(R.drawable.ic_img_cooler_step1);
                mBinding.step2Img.setBackgroundResource(R.drawable.img_brush_guide1);
                mBinding.step3Img.setBackgroundResource(R.drawable.img_cooler_guide3);
                mBinding.step4Img.setBackgroundResource(R.drawable.ic_img_cooler_step4);
                setGuideText(getString(R.string.cooler_step1), getString(R.string.cooler_step2), getString(R.string.cooler_step3), getString(R.string.cooler_step4));
                mBinding.headNum.setText("   " + SharedPreferencesPackage.getCoolerID(this));
                break;
            case "Puff":
                // step img, text change
                mBinding.headImg.setBackgroundResource(R.drawable.img_head_puff_big);
                mBinding.step1Img.setBackgroundResource(R.drawable.ic_img_cooler_step1);
                mBinding.step2Img.setBackgroundResource(R.drawable.img_brush_guide1);
                mBinding.step3Img.setBackgroundResource(R.drawable.img_cooler_guide3);
                mBinding.step4Img.setBackgroundResource(R.drawable.ic_img_cooler_step1);
                setGuideText(getString(R.string.puff_step1), getString(R.string.puff_step2), getString(R.string.puff_step3), getString(R.string.puff_step4));
                mBinding.headNum.setText("   " + SharedPreferencesPackage.getPuffID(this));
                break;
            case "Silicone":
                // step img, text change
                mBinding.headImg.setBackgroundResource(R.drawable.img_head_sillicon_big);
                mBinding.step1Img.setBackgroundResource(R.drawable.img_brush_guide1);
                mBinding.step2Img.setBackgroundResource(R.drawable.img_brush_guide2);
                mBinding.step3Img.setBackgroundResource(R.drawable.img_silicon_guide3);
                mBinding.step4Img.setBackgroundResource(R.drawable.img_brush_guide4);
                setGuideText(getString(R.string.silicon_step1), getString(R.string.silicon_step2), getString(R.string.silicon_step3), getString(R.string.silicon_step4));
                mBinding.headNum.setText("   " + SharedPreferencesPackage.getSiliconID(this));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK);
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
        int count;
        if (total == 0) {
            count = 0;
            mBinding.zeroCount.setContentDescription("최저 0회");
            mBinding.totalCount.setText("0");
            mBinding.totalCount.setContentDescription("총 사용횟수 0회");
        } else {
            count = head * 100 / total;
            mBinding.zeroCount.setContentDescription("최저 0회");
            mBinding.totalCount.setText(String.valueOf(total));
            mBinding.totalCount.setContentDescription("최대 " + (total) + "회");
        }

        mBinding.useCount.setText(countTxt);
        mBinding.useCount.setContentDescription("총 사용횟수 " + countTxt);
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
