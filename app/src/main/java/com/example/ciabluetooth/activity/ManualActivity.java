package com.example.ciabluetooth.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;

import com.example.ciabluetooth.R;
import com.example.ciabluetooth.adapter.ViewPagerAdapter;
import com.example.ciabluetooth.databinding.ActivityManualBinding;
import com.example.ciabluetooth.fragment.manual.ManualHeadFragment;
import com.example.ciabluetooth.fragment.manual.ManualStartFragment;
import com.example.ciabluetooth.fragment.manual.ManualUseFragment;
import com.google.android.material.navigation.NavigationView;

public class ManualActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String TAG = this.getClass().getSimpleName();
    private ActivityManualBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(ManualActivity.this, R.layout.activity_manual);
        mBinding.setView(ManualActivity.this);

//        setSupportActionBar(mBinding.manualLayoutToolbar); // 툴바를 액티비티의 앱바로 지정
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 드로어를 꺼낼 홈 버튼 활성화
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_btn_menu); // 홈버튼 이미지 변경
//        getSupportActionBar().setDisplayShowTitleEnabled(false); // 툴바에 타이틀 안보이게
        mBinding.manualNavigationView.setNavigationItemSelectedListener(this);
        getTabs();
    }

    private void getTabs() {
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                viewPagerAdapter.addFragment(ManualStartFragment.getInstance(), getString(R.string.manual_start));
                viewPagerAdapter.addFragment(ManualUseFragment.getInstance(), getString(R.string.manual_usage));
                viewPagerAdapter.addFragment(ManualHeadFragment.getInstance(), getString(R.string.manual_head));

                mBinding.viewPager.setAdapter(viewPagerAdapter);
                mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
                setupTabIcons();
            }
        });
    }

    private void setupTabIcons() {
        View viewFirst = getLayoutInflater().inflate(R.layout.custom_tab_left, null);
        ImageView imgFirst = viewFirst.findViewById(R.id.img_tab);
        TextView txtFirst = viewFirst.findViewById(R.id.txt_tab);
        imgFirst.setImageResource(R.drawable.ic_img_tri_gray);
        txtFirst.setText(R.string.manual_start);
        mBinding.tabLayout.getTabAt(0).setCustomView(viewFirst);

        View viewSecond = getLayoutInflater().inflate(R.layout.custom_tab, null);
        ImageView imgSecond = viewSecond.findViewById(R.id.img_tab);
        TextView txtSecond = viewSecond.findViewById(R.id.txt_tab);
        imgSecond.setImageResource(R.drawable.ic_img_tri_gray);
        txtSecond.setText(R.string.manual_usage);
        mBinding.tabLayout.getTabAt(1).setCustomView(viewSecond);

        View viewThird = getLayoutInflater().inflate(R.layout.custom_tab_right, null);
        ImageView imgThird = viewThird.findViewById(R.id.img_tab);
        TextView txtThird = viewThird.findViewById(R.id.txt_tab);
        imgThird.setImageResource(R.drawable.ic_img_tri_gray);
        txtThird.setText(R.string.manual_head);
        mBinding.tabLayout.getTabAt(2).setCustomView(viewThird);

    }
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        mBinding.manualDrawerLayout.openDrawer(GravityCompat.END);
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu1:
                startActivity(DrawerActivity.class, getString(R.string.menu1));
                break;
            case R.id.menu2:
                startActivity(DrawerActivity.class, getString(R.string.menu2));
                break;
            case R.id.menu3:
                startActivity(DrawerActivity.class, getString(R.string.menu3));
                break;
            case R.id.menu4:
                startActivity(DrawerActivity.class, getString(R.string.menu4));
                break;
        }
        return false;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;
            case R.id.menu_btn:
                mBinding.manualDrawerLayout.openDrawer(GravityCompat.END);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        View header = mBinding.manualNavigationView.getHeaderView(0);
        ImageView drawerCloseBtn = header.findViewById(R.id.drawer_close_btn);
        drawerCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void startActivity(Class className, String title) {
        Intent intent = new Intent(ManualActivity.this, className);
        intent.putExtra("title", title);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (mBinding.manualDrawerLayout.isDrawerOpen(GravityCompat.END)) {
            mBinding.manualDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }
}
