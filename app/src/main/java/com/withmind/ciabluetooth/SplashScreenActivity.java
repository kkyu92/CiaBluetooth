package com.withmind.ciabluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.withmind.ciabluetooth.activity.MainActivity;
import com.withmind.ciabluetooth.adapter.ExpandableListAdapter;
import com.withmind.ciabluetooth.data.Permission;
import com.withmind.ciabluetooth.databinding.ActivitySplashScreenBinding;
import com.withmind.ciabluetooth.profile.BleProfileService;
import com.withmind.ciabluetooth.profile.BleProfileServiceReadyActivity;
import com.withmind.ciabluetooth.uart.UARTInterface;
import com.withmind.ciabluetooth.uart.UARTLocalLogContentProvider;
import com.withmind.ciabluetooth.uart.UARTService;
import com.withmind.ciabluetooth.util.SharedPreferencesPackage;

import java.util.ArrayList;
import java.util.UUID;

import static com.withmind.ciabluetooth.Constants.REQUEST_CONNECT;

public class SplashScreenActivity extends BleProfileServiceReadyActivity<UARTService.UARTBinder> implements UARTInterface {
    private static final String TAG = SplashScreenActivity.class.getSimpleName();
    private ActivitySplashScreenBinding mBinding;
    private static final int DURATION = 1000;
    public BluetoothManager bluetoothManager;
    public BluetoothAdapter bluetoothAdapter;
    public BroadcastReceiver bluetoothReceiver;

    private UARTService.UARTBinder serviceBinder;

    private static final String[] permission = {
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @Override
    protected void onCreateView(final Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(SplashScreenActivity.this, R.layout.activity_splash_screen);

//        Log.e(TAG, SharedPreferencesPackage.getDeviceAddress(this) + "\n" + SharedPreferencesPackage.getDeviceID(this));
        SharedPreferencesPackage.setNowActivityName(this, TAG);
        ArrayList<Permission> data = new ArrayList<>();
        Permission item = new Permission();
        item.type = Constants.EXPANDABLE_PARENT;
        item.text = getString(R.string.permit_access);
        item.icon = R.drawable.icon_device;
        data.add(item);
        item = new Permission();
        item.type = Constants.EXPANDABLE_CHILD;
        item.text = getString(R.string.permit_bluetooth_explain);
        data.add(item);
        item = new Permission();
        item.type = Constants.EXPANDABLE_PARENT;
        item.text = getString(R.string.permit_bluetooth);
        item.icon = R.drawable.ic_icon_bluetooth;
        data.add(item);
        item = new Permission();
        item.type = Constants.EXPANDABLE_CHILD;
        item.text = getString(R.string.permit_access_explain);
        data.add(item);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        final RecyclerView.ItemAnimator animator = mBinding.recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        mBinding.recyclerView.setAdapter(new ExpandableListAdapter(SplashScreenActivity.this, data));

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mBinding.permissionView.setVisibility(View.GONE);
                Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.start_animation);
                mBinding.title.startAnimation(startAnimation);
                mBinding.img.startAnimation(startAnimation);
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.permissionView.setVisibility(View.VISIBLE);
                if (!isBLEEnabled() && mBinding.permit.getVisibility() == View.GONE) {
                    showBLEDialog();
                } else if (isBLEEnabled()) {
                    mBinding.btnConnect.setText(R.string.bluetooth_show_list);
                }
                if (isPermission(permission)) {
                    onRequestPermission(mPermissionListener, permission);
                }
            }
        }, 2500);

        mBinding.btnOk.setOnClickListener(mOnClickListener);
        mBinding.btnCancel.setOnClickListener(mOnClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBinding.btnConnect.getText().toString().equals(getString(R.string.action_disconnect)) || mBinding.btnConnect.getText().toString().equals(getString(R.string.action_connect))) {
            startIntroView(1);
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_ok:
                    onRequestPermission(mPermissionListener, permission);
                    break;
                case R.id.btn_cancel:
                    Toast.makeText(SplashScreenActivity.this, getString(R.string.not_permission), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private PermissionListener mPermissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            mBinding.permit.setVisibility(View.GONE);
            mBinding.connect.setVisibility(View.VISIBLE);
            Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink_animation);
            mBinding.blinkImg.startAnimation(startAnimation);
            showBLEDialog();
        }

        @Override
        public void onPermissionDenied() {
            Toast.makeText(SplashScreenActivity.this, getString(R.string.not_permission), Toast.LENGTH_SHORT).show();
        }
    };

    private void startIntroView(int splash) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (splash == 1) {
                    mBinding.permissionView.setVisibility(View.GONE);
                    Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.start_animation);
                    mBinding.title.startAnimation(startAnimation);
                    mBinding.img.startAnimation(startAnimation);
                } else {
                    mBinding.introView.setVisibility(View.GONE);
                    mBinding.permitContainer.setVisibility(View.GONE);
                    Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.start_animation);
                    mBinding.permissionView.startAnimation(startAnimation);
                }
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                send(getString(R.string.get_info));
            }
        }, 1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                SplashScreenActivity.this.startActivity(intent);
                SplashScreenActivity.this.finish();
            }
        }, 2500);
    }

    @Override
    public void onBackPressed() {
        // We don't want the splash screen to be interrupted
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_ENABLE_BT: // 25번 줄에서 requestCode 값 1
                if (resultCode == RESULT_OK) {
                    // 블루투스 기능을 켰을 때
                    if (isConnected()) {
                        startIntroView(1);
                    } else {
                        mBinding.btnConnect.setText(R.string.bluetooth_show_list);
                        onConnectClicked(mBinding.btnConnect);
                        if (SharedPreferencesPackage.getDeviceID(this).equals("")) {
                            Toast.makeText(this, "블루투스 기기를 연결해 주세요.", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(this, "블루투스 거부.", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CONNECT:
                break;
        }
    }

//    public Handler mHandler = new Handler(Looper.getMainLooper()) {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case Constants.MESSAGE_CONNECT:
//                    bluetoothReceiver =  getConnectBluetoothDevice();
//                    break;
//            }
//        }
//    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (bluetoothReceiver != null) {
//            unregisterReceiver(bluetoothReceiver);
//        }
    }

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
    public void onDeviceSelected(@NonNull final BluetoothDevice device, final String name) {
        // The super method starts the service
        super.onDeviceSelected(device, name);
        if (name != null && name.contains(getString(R.string.cia_id))) {
            if (name.contains(SharedPreferencesPackage.getDeviceID(this))) {
                SharedPreferencesPackage.setDeviceAddress(this, device.getAddress());
                startIntroView(2);
                Toast.makeText(this, device + " + " + name + " + " + device.getAddress(), Toast.LENGTH_LONG).show();
            } else {
                startIntroView(2);
                Toast.makeText(this, "새로운 기기 연결", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, " AU 디바이스를 연결해주세요. ", Toast.LENGTH_LONG).show();
        }
        Log.e(TAG, device + " + " + name);
    }

    @Override
    public void onUnConnected() {
        runOnUiThread(() -> {
            mBinding.introView.setVisibility(View.GONE);
            mBinding.permitContainer.setVisibility(View.GONE);
            Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.start_animation);
            mBinding.permissionView.startAnimation(startAnimation);
        });
        new Handler().postDelayed(() -> {
            final Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            SplashScreenActivity.this.startActivity(intent);
            SplashScreenActivity.this.finish();
        }, 2500);
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
        if (serviceBinder != null)
            serviceBinder.send(text);
    }
}
