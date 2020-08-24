package com.example.ciabluetooth.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.ciabluetooth.Constants;
import com.example.ciabluetooth.SplashScreenActivity;
import com.example.ciabluetooth.util.SharedPreferencesPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static android.bluetooth.BluetoothProfile.GATT;
import static android.bluetooth.BluetoothProfile.GATT_SERVER;

public class BaseActivity extends AppCompatActivity {
    // app version
    public static final int THIS_APP_VERSION = 1;
    public static int WIDTH = 0;
    public static int HEIGHT = 0;

    private static final String TAG = BaseActivity.class.getSimpleName();

    public static String INIT = "";
    public PermissionListener mListener = null;

    public int brushCount;
    public int coolerCount;
    public int puffCount;
    public int siliconCount;
    public int allCount;

    public String connectedName, connectedMac;

    public interface PermissionListener {
        void onPermissionGranted();

        void onPermissionDenied();
    }

    public void saveData(int brushCount, int coolerCount, int puffCount, int siliconCount) {
        brushCount = 100;
        coolerCount = 200;
        puffCount = 300;
        siliconCount = 400;
        allCount = brushCount + coolerCount + puffCount + siliconCount;

        int lastBrush = SharedPreferencesPackage.getBrushLastMonth(this);
        int lastCooler = SharedPreferencesPackage.getCoolerLastMonth(this);
        int lastPuff = SharedPreferencesPackage.getPuffLastMonth(this);
        int lastSilicon = SharedPreferencesPackage.getSiliconLastMonth(this);
        int lastAll = SharedPreferencesPackage.getAllLastMonth(this);

        // 공통
        // 이번달 count 저장 [ getData - lastMonth ]
        SharedPreferencesPackage.setBrushThisMonth(this, brushCount - lastBrush);
        SharedPreferencesPackage.setCoolerThisMonth(this, coolerCount - lastCooler);
        SharedPreferencesPackage.setPuffThisMonth(this, puffCount - lastPuff);
        SharedPreferencesPackage.setSiliconThisMonth(this, siliconCount - lastSilicon);
        SharedPreferencesPackage.setAllThisMonth(this, allCount - lastAll);

//        Log.e(TAG, "thisBrush ::: " + SharedPreferencesPackage.getBrushThisMonth(this));
//        Log.e(TAG, "thisCooler ::: " + SharedPreferencesPackage.getCoolerThisMonth(this));
//        Log.e(TAG, "thisPuff ::: " + SharedPreferencesPackage.getPuffThisMonth(this));
//        Log.e(TAG, "thisSilicon ::: " + SharedPreferencesPackage.getSiliconThisMonth(this));
//        Log.e(TAG, "all ::: " + SharedPreferencesPackage.getAllThisMonth(this));
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
//        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics metrics = new DisplayMetrics();
//        assert wm != null;
//        wm.getDefaultDisplay().getMetrics(metrics);
//        WIDTH = metrics.widthPixels;
//        HEIGHT = metrics.heightPixels;
//        Log.e(TAG, "metrics width::: " + metrics.widthPixels);
//        Log.e(TAG, "metrics height::: " + metrics.heightPixels);
//        Log.e(TAG, "checkInternetState");
//        checkInternetState();
        if (INIT == null) {
            Intent intent = new Intent(this, SplashScreenActivity.class);
            startActivity(intent);
            finish();
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

//    public void checkInternetState() {
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
//        if (!(connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected())) {
//            new AlertDialog.Builder(this)
//                    .setMessage("네트워크 연결상태를 확인해 주세요.")
//                    .setCancelable(false)
//                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            finishAffinity();
//                        }
//                    })
//                    .show();
//        }
//    }

    public void onRequestPermission(PermissionListener listener, String[] permission) {
        mListener = listener;

        ArrayList<String> permissionList = new ArrayList<>();
        for (String aPermission : permission) {
            if (ContextCompat.checkSelfPermission(this, aPermission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(aPermission);
            }
        }

        if (permissionList.size() > 0) {
            String[] permissions = new String[permissionList.size()];
            for (int i = 0; i < permissions.length; i++) {
                permissions[i] = permissionList.get(i);
            }
            ActivityCompat.requestPermissions(this, permissions, Constants.PERMISSIONS_DATA);
        } else {
            Log.i(TAG, "onRequestPermission");
            if (mListener != null) {
                mListener.onPermissionGranted();
            }
        }
    }

    public boolean isPermission(String[] permissions) {
        boolean isPermission = true;

        for (String permission : permissions) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, permission);

            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                isPermission = false;
            }
        }
        return isPermission;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSIONS_DATA:
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        if (mListener != null) {
                            mListener.onPermissionDenied();
                            return;
                        }
                    }
                }
                if (mListener != null) {
                    Log.i(TAG, "onRequestPermissionsResult");
                    mListener.onPermissionGranted();
                }
                break;
        }
    }

    public Handler mToasHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constants.MESSAGE_START_TOAST:
                    Toast.makeText(BaseActivity.this, String.valueOf(msg.obj), Toast.LENGTH_SHORT).show();
                    break;
                case Constants.MESSAGE_INTENT_BACK:
                    ComponentName compName = new ComponentName("com.example.ciabluetooth", "com.example.ciabluetooth.activity.MainActivity");
//                    ComponentName compName = new ComponentName("com.example.ciabluetooth", "com.example.ciabluetooth.SplashScreenActivity");
                    //패키지명은 연락처  액티비티명은 최근기록 입력
                    Intent intentBack = new Intent(Intent.ACTION_MAIN);
                    intentBack.addCategory(Intent.CATEGORY_LAUNCHER);
                    intentBack.setComponent(compName);
                    startActivity(intentBack);
                    Toast.makeText(BaseActivity.this, connectedName + connectedMac, Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    public BroadcastReceiver getConnectBluetoothDevice() {
        //블루투스 Adapter를 가져온다
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);

        if (bluetoothAdapter == null) {
            // 만약 블루투스 adapter가 없으면, 블루투스를 지원하지 않는 기기이거나 블루투스 기능을 끈 기기이다.
        } else {
            // 블루투스 adapter가 있으면, 블루투스 adater에서 페어링된 장치 목록을 불러올 수 있다.
//            List<BluetoothDevice> pairDevices = bluetoothManager.getConnectedDevices(GATT_SERVER);
            Set<BluetoothDevice> pairDevices = bluetoothAdapter.getBondedDevices();
            Log.e(TAG, "size::: "+pairDevices.size());
            // Bluetooth 가 켜져있는 상테에서 들어온 경우 - 페어링된 장치가 있으면
            if (pairDevices.size() > 0) {
                for (BluetoothDevice device : pairDevices) {
                    //페어링된 장치 이름과, MAC주소를 가져올 수 있다.
                    Log.e("pairingList-name", device.getName());
                    Log.e("pairingList-mac", device.getAddress()+"\n"+pairDevices.size());
                }
            } else {
                Toast.makeText(getApplicationContext(), "Cia 기기와 블루투스 연결이 필요합니다.", Toast.LENGTH_LONG).show();
            }
        }

        //브로드캐스트리시버를 이용하여 블루투스 장치가 연결이 되고, 끊기는 이벤트를 받아 올 수 있다.
        BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                //연결된 장치를 intent를 통하여 가져온다.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.e(TAG, "이미 연결되어 있는 기기를 가져오는가? ::: "+device.getName());
                //장치가 연결이 되었으면
                if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                    Log.e("connect", device.getName() + " Device Is Connected!");

                    connectedName = device.getName();
                    connectedMac = device.getAddress();
                    mToasHandler.sendEmptyMessageDelayed(Constants.MESSAGE_INTENT_BACK, 1000);

                    //장치의 연결이 끊기면
                } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                    Log.e("disConnect", device.getName() + " Device Is DISConnected!");
                } else {
                    Log.e(TAG, "이미 연결되어 있는 기기를 가져오는가? ::: "+device.getName());
                }
            }
        };

        //MUST unregisterReceiver(bluetoothReceiver) in onDestroy()
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
        registerReceiver(bluetoothReceiver, filter);
        filter = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(bluetoothReceiver, filter);
        return bluetoothReceiver;
    }
}