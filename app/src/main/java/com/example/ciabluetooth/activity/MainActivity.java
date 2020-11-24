package com.example.ciabluetooth.activity;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.ciabluetooth.Constants;
import com.example.ciabluetooth.R;
import com.example.ciabluetooth.SplashScreenActivity;
import com.example.ciabluetooth.databinding.ActivityMainBinding;
import com.example.ciabluetooth.profile.BleProfileService;
import com.example.ciabluetooth.profile.BleProfileServiceReadyActivity;
import com.example.ciabluetooth.uart.UARTInterface;
import com.example.ciabluetooth.uart.UARTLocalLogContentProvider;
import com.example.ciabluetooth.uart.UARTService;
import com.example.ciabluetooth.util.Common;
import com.example.ciabluetooth.util.CustomDialog;
import com.example.ciabluetooth.util.SharedPreferencesPackage;

import java.util.UUID;

public class MainActivity extends BleProfileServiceReadyActivity<UARTService.UARTBinder> implements UARTInterface {
    private String TAG = this.getClass().getSimpleName();
    private ActivityMainBinding mBinding;
    private CustomDialog dialog;

    public int brushCnt, brushRun;
    public int coolerCnt, coolerRun;
    public int puffCnt, puffRun;
    public int siliconCnt, siliconRun;
    public int allCnt, allRun;
    public int head;
    public int battery;

    private UARTService.UARTBinder serviceBinder;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        mBinding.setView(MainActivity.this);

        SharedPreferencesPackage.setNowActivityName(this, TAG);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume : start");
        //TODO: onResume 이 아니라 onActivityResult 로 변경해야한다 ScannerFragment 에서 엑티비티 실행으로 하는것은 finish
        if (SharedPreferencesPackage.getIsConnected(this)) {
            Log.e(TAG, "onResume : 연결됨");
            onConnectClicked(null);
            send(getString(R.string.get_info));
            runOnUiThread(() -> {
                mBinding.loading.setVisibility(View.VISIBLE);
            });
            new Handler().postDelayed(() -> {
                saveData();
                mBinding.loading.setVisibility(View.GONE);
            }, 1000);
        } else {
            Log.e(TAG, "onResume : 연결안됨");
            Toast.makeText(this, "블루투스 연결을 확인해주세요.", Toast.LENGTH_LONG).show();
            saveData();
        }
        Log.e(TAG, "onResume : end");
    }

    private void saveData() {
        Log.e(TAG, "refreshData : start");
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
        Log.e(TAG, brushCnt + "\n" + coolerCnt + "\n" + puffCnt + "\n" + siliconCnt);

        mBinding.cardBrushCount.setText(String.valueOf(brushCnt));
        mBinding.cardCoolerCount.setText(String.valueOf(coolerCnt));
        mBinding.cardPuffCount.setText(String.valueOf(puffCnt));
        mBinding.cardSiliconCount.setText(String.valueOf(siliconCnt));

        setData(battery);
        // setText(battery) && 20이상부터 텍스트 보이게
        if (battery >= 20) {
            mBinding.percentText.setText(battery + getString(R.string.per));
            mBinding.percentText.setContentDescription("남은 배터리는 " + battery + getString(R.string.per) + "입니다.");
            mBinding.percentTextContainer.setVisibility(View.VISIBLE);
        } else {
            mBinding.percentText.setContentDescription("남은 배터리가 없습니다.");
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
        Log.e(TAG, "refreshData : end");
    }

    private void startActivity(Class className, int request) {
        Intent intent = new Intent(MainActivity.this, className);
        startActivityForResult(intent, request);
    }

    private void headStartActivity(String name, int headRun, int headCnt) {
        Intent head = new Intent(MainActivity.this, HeadActivity.class);
        head.putExtra("name", name);
        head.putExtra("headRun", headRun);
        head.putExtra("headCnt", headCnt);
        head.putExtra("allRun", allRun);
        head.putExtra("allCnt", allCnt);
        startActivityForResult(head, Constants.INTENT_REQUEST_HEAD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.INTENT_REQUEST_HEAD:
            case Constants.INTENT_REQUEST_INFO:
            case Constants.INTENT_REQUEST_MANUAL:
            case Constants.INTENT_REQUEST_PATTERN:
                Log.e(TAG, "갔다옴");
                if (SharedPreferencesPackage.getIsConnected(this)) {
                    Log.e(TAG, "연결 되어있는 상태");
                } else {
                    Log.e(TAG, "끊김 재연결 하는과정 시작");
                    Toast.makeText(this, "블루투스 연결을 확인해주세요.", Toast.LENGTH_LONG).show();
//                    onConnectClicked(null);
//                    send(getString(R.string.get_info));
//                    saveData();
                }
                break;
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.plus_btn:
                onConnectClicked(null);
                send(getString(R.string.get_info));
                runOnUiThread(() -> {
                    mBinding.loading.setVisibility(View.VISIBLE);
                });
                new Handler().postDelayed(() -> {
                    saveData();
                    mBinding.loading.setVisibility(View.GONE);
                }, 1000);
                break;
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
                startActivity(CiaInfoActivity.class, Constants.INTENT_REQUEST_INFO);
                break;
            case R.id.manual_btn:
                startActivity(ManualActivity.class, Constants.INTENT_REQUEST_MANUAL);
                break;
            case R.id.pattern_btn:
                startActivity(PatternActivity.class, Constants.INTENT_REQUEST_PATTERN);
                break;
        }
    }

    // percent line
    private void setData(int count) {
        new StartBar().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, count);
    }

    @Override
    public void onUnConnected() {
    }

    private class StartBar extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... integers) {
            Common.setPercentGaugeWidth(mBinding.line, mBinding.percentLine, integers[0]);
            return null;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        dialog = new CustomDialog();
        dialog.setNoticeType(MainActivity.this, Constants.DIALOG_APP_FINISH);
        dialog.setOnDialogClick(mOnDialogClick);
        dialog.setCancelable(true);
        dialog.show(MainActivity.this.getSupportFragmentManager(), Constants.APP_FINISH_DIALOG);
    }

    // 홈 클릭 했을때 나갈건지 체크 하는 dialog
    private CustomDialog.OnDialogClick mOnDialogClick = new CustomDialog.OnDialogClick() {
        @Override
        public void onDialogClick(int type, boolean isOk) {
            if (isOk) {
                if (type == Constants.DIALOG_APP_FINISH) {
                    finishAndRemoveTask();
                }
            } else {
                if (type == Constants.DIALOG_APP_FINISH) {
                    dialog.dismiss();
                }
            }
        }
    };


    @Override
    protected Class<? extends BleProfileService> getServiceClass() {
        return UARTService.class;
    }

    @Override
    protected int getLoggerProfileTitle() {
        return R.string.uart_feature_title;
    }

    @Override
    protected Uri getLocalAuthorityLogger() {
        return UARTLocalLogContentProvider.AUTHORITY_URI;
    }

    @Override
    protected void setDefaultUI() {
        // empty
    }

    @Override
    protected void onServiceBound(final UARTService.UARTBinder binder) {
        serviceBinder = binder;
    }

    @Override
    protected void onServiceUnbound() {
        serviceBinder = null;
    }

    @Override
    protected void onViewCreated(final Bundle savedInstanceState) {
    }

    @Override
    protected void onRestoreInstanceState(final @NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onServicesDiscovered(@NonNull final BluetoothDevice device, final boolean optionalServicesFound) {
        // do nothing
    }

    @Override
    protected int getDefaultDeviceName() {
        return R.string.uart_default_name;
    }

    @Override
    protected int getAboutTextId() {
        return R.string.uart_about_text;
    }

    @Override
    protected UUID getFilterUUID() {
        return null; // not used
    }

    @Override
    public void send(final String text) {
        Log.e(TAG, "send");
        if (serviceBinder != null) {
            Log.e(TAG, "send?");
            serviceBinder.send(text);
        } else {
            Log.e(TAG, "no send");
        }
    }
}
