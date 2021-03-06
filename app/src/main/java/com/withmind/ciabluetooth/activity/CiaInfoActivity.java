package com.withmind.ciabluetooth.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.withmind.ciabluetooth.R;
import com.withmind.ciabluetooth.adapter.ViewPagerAdapter;
import com.withmind.ciabluetooth.databinding.ActivityCiaInfoBinding;
import com.withmind.ciabluetooth.fragment.info.InfoBatteryFragment;
import com.withmind.ciabluetooth.fragment.info.InfoDeviceFragment;
import com.withmind.ciabluetooth.fragment.info.InfoTimeFragment;
import com.withmind.ciabluetooth.util.SharedPreferencesPackage;
import com.google.android.material.tabs.TabLayout;

public class CiaInfoActivity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();
    private ActivityCiaInfoBinding mBinding;
    private Animation fadeOutIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        mBinding = DataBindingUtil.setContentView(CiaInfoActivity.this, R.layout.activity_cia_info);
        mBinding.setView(CiaInfoActivity.this);

        fadeOutIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out_in);
        getTabs();
    }

    private void getTabs() {
        Log.e(TAG, "getTabs");
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                viewPagerAdapter.addFragment(InfoDeviceFragment.getInstance(), getString(R.string.device_info));
                viewPagerAdapter.addFragment(InfoBatteryFragment.getInstance(SharedPreferencesPackage.getDeviceBattery(CiaInfoActivity.this)), getString(R.string.battery));
                viewPagerAdapter.addFragment(InfoTimeFragment.getInstance(), getString(R.string.info_time));

                mBinding.viewPager.setAdapter(viewPagerAdapter);
                mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
            }
        });
    }

    private void setupTabIcons(int tabIndex) {
        mBinding.tabLayout.getTabAt(0).setCustomView(null);
        mBinding.tabLayout.getTabAt(1).setCustomView(null);
        mBinding.tabLayout.getTabAt(2).setCustomView(null);

        Log.e(TAG, "setupTabIcons");
        View viewFirst = getLayoutInflater().inflate(R.layout.custom_tab_left, null);
        ImageView imgFirst = viewFirst.findViewById(R.id.img_tab);
        TextView txtFirst = viewFirst.findViewById(R.id.txt_tab);
        imgFirst.setImageResource(R.drawable.ic_img_tri_gray);
        txtFirst.setText(R.string.device_info);
        mBinding.tabLayout.getTabAt(0).setCustomView(viewFirst);

        View viewSecond = getLayoutInflater().inflate(R.layout.custom_tab, null);
        ImageView imgSecond = viewSecond.findViewById(R.id.img_tab);
        TextView txtSecond = viewSecond.findViewById(R.id.txt_tab);
        imgSecond.setImageResource(R.drawable.ic_img_tri_gray);
        txtSecond.setText(R.string.battery);
        mBinding.tabLayout.getTabAt(1).setCustomView(viewSecond);

        View viewThird = getLayoutInflater().inflate(R.layout.custom_tab_right, null);
        ImageView imgThird = viewThird.findViewById(R.id.img_tab);
        TextView txtThird = viewThird.findViewById(R.id.txt_tab);
        imgThird.setImageResource(R.drawable.ic_img_tri_gray);
        txtThird.setText(R.string.info_time);
        mBinding.tabLayout.getTabAt(2).setCustomView(viewThird);

        if (tabIndex == 0) {
            txtFirst.setTypeface(Typeface.DEFAULT_BOLD);
            txtSecond.setTypeface(Typeface.DEFAULT);
            txtThird.setTypeface(Typeface.DEFAULT);
        } else if (tabIndex == 1) {
            txtFirst.setTypeface(Typeface.DEFAULT);
            txtSecond.setTypeface(Typeface.DEFAULT_BOLD);
            txtThird.setTypeface(Typeface.DEFAULT);
        } else {
            txtFirst.setTypeface(Typeface.DEFAULT);
            txtSecond.setTypeface(Typeface.DEFAULT);
            txtThird.setTypeface(Typeface.DEFAULT_BOLD);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        mBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mBinding.viewPager.setCurrentItem(tab.getPosition());
                mBinding.deviceImg.startAnimation(fadeOutIn);
                if (tab.getPosition() == 0) {
                    mBinding.deviceImg.setImageDrawable(getResources().getDrawable(R.drawable.img_splash_device_left));
                    setupTabIcons(0);
                } else if (tab.getPosition() == 1) {
                    mBinding.deviceImg.setImageDrawable(getResources().getDrawable(R.drawable.img_splash_device_big));
                    setupTabIcons(1);
                } else {
                    mBinding.deviceImg.setImageDrawable(getResources().getDrawable(R.drawable.img_splash_device_right));
                    setupTabIcons(2);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK);
        finish();
    }
}
