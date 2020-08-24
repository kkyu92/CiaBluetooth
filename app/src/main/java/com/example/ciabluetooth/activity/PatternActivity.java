package com.example.ciabluetooth.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.ciabluetooth.R;
import com.example.ciabluetooth.adapter.ViewPagerAdapter;
import com.example.ciabluetooth.databinding.ActivityPatternBinding;
import com.example.ciabluetooth.fragment.pattern.PatternMonthlyFragment;
import com.example.ciabluetooth.fragment.pattern.PatternTimeFragment;

public class PatternActivity extends BaseActivity {
    private String TAG = this.getClass().getSimpleName();
    private ActivityPatternBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(PatternActivity.this, R.layout.activity_pattern);
        mBinding.setView(PatternActivity.this);

        getTabs();
    }

    private void setupTabIcons() {
        View viewFirst = getLayoutInflater().inflate(R.layout.custom_tab_left, null);
        ImageView imgFirst = viewFirst.findViewById(R.id.img_tab);
        TextView txtFirst = viewFirst.findViewById(R.id.txt_tab);
        imgFirst.setImageResource(R.drawable.ic_img_tri_gray);
        txtFirst.setText(R.string.manual_start);
        mBinding.tabLayout.getTabAt(0).setCustomView(viewFirst);

        View viewThird = getLayoutInflater().inflate(R.layout.custom_tab_right, null);
        ImageView imgThird = viewThird.findViewById(R.id.img_tab);
        TextView txtThird = viewThird.findViewById(R.id.txt_tab);
        imgThird.setImageResource(R.drawable.ic_img_tri_gray);
        txtThird.setText(R.string.manual_head);
        mBinding.tabLayout.getTabAt(1).setCustomView(viewThird);

    }

    private void getTabs() {
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        final String stringData = "stringData";
        final String time = "50";
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                viewPagerAdapter.addFragment(PatternMonthlyFragment.getInstance(stringData), "월간패턴");
                viewPagerAdapter.addFragment(PatternTimeFragment.getInstance(time), "사용시간");

                mBinding.viewPager.setAdapter(viewPagerAdapter);
                mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
                setupTabIcons();
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
}
