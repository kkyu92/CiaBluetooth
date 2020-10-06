/*
 * Copyright (c) 2015, Nordic Semiconductor
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.example.ciabluetooth.scanner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.ciabluetooth.Constants;
import com.example.ciabluetooth.R;
import com.example.ciabluetooth.SplashScreenActivity;
import com.example.ciabluetooth.activity.MainActivity;
import com.example.ciabluetooth.util.SharedPreferencesPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat;
import no.nordicsemi.android.support.v18.scanner.ScanCallback;
import no.nordicsemi.android.support.v18.scanner.ScanFilter;
import no.nordicsemi.android.support.v18.scanner.ScanResult;
import no.nordicsemi.android.support.v18.scanner.ScanSettings;

/**
 * ScannerFragment class scan required BLE devices and shows them in a list. This class scans and filter
 * devices with standard BLE Service UUID and devices with custom BLE Service UUID. It contains a
 * list and a button to scan/cancel. There is a interface {@link OnDeviceSelectedListener} which is
 * implemented by activity in order to receive selected device. The scanning will continue to scan
 * for 5 seconds and then stop.
 */
public class ScannerFragment extends DialogFragment {
    private final static String TAG = "ScannerFragment";

    private final static String PARAM_UUID = "param_uuid";
    private final static long SCAN_DURATION = 5000;
    private final static long SCAN_NULL = 3500;
//    private final static long SCAN_MAIN = 2500;
    private final static long SCAN_MAIN = 500;

    private final static int REQUEST_PERMISSION_REQ_CODE = 34; // any 8-bit number

    private BluetoothAdapter bluetoothAdapter;
    private OnDeviceSelectedListener listener;
    private DeviceListAdapter adapter;
    private final Handler handler = new Handler();
    private LinearLayout container;
    private Button scanButton;

    private TextView title;

    private View permissionRationale;

    private ParcelUuid uuid;

    private boolean scanning = false;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private String deviceAddress, deviceID, name;

    public static ScannerFragment getInstance(final UUID uuid, String alreadyD) {
        final ScannerFragment fragment = new ScannerFragment();

        final Bundle args = new Bundle();
        if (uuid != null) {
            args.putParcelable(PARAM_UUID, new ParcelUuid(uuid));
        }
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Interface required to be implemented by activity.
     */
    public interface OnDeviceSelectedListener {
        /**
         * Fired when user selected the device.
         *
         * @param device the device to connect to
         * @param name   the device name. Unfortunately on some devices {@link BluetoothDevice#getName()}
         *               always returns <code>null</code>, i.e. Sony Xperia Z1 (C6903) with Android 4.3.
         *               The name has to be parsed manually form the Advertisement packet.
         */
        void onDeviceSelected(@NonNull final BluetoothDevice device, @Nullable final String name);

        /**
         * Fired when scanner dialog has been cancelled without selecting a device.
         */
        void onDialogCanceled();
        void onUnConnected();
    }

    /**
     * This will make sure that {@link OnDeviceSelectedListener} interface is implemented by activity.
     */
    @Override
    public void onAttach(@NonNull final Context context) {
        super.onAttach(context);
        try {
            this.listener = (OnDeviceSelectedListener) context;
        } catch (final ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnDeviceSelectedListener");
        }
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        if (args != null && args.containsKey(PARAM_UUID)) {
            uuid = args.getParcelable(PARAM_UUID);
        }

        final BluetoothManager manager = (BluetoothManager) requireContext().getSystemService(Context.BLUETOOTH_SERVICE);
        if (manager != null) {
            bluetoothAdapter = manager.getAdapter();
        }
    }

    @Override
    public void onDestroyView() {
        stopScan();
        super.onDestroyView();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(requireContext());
        View dialogView = LayoutInflater.from(requireContext())
                .inflate(R.layout.fragment_device_selection, null);
        View dialogTitleView = LayoutInflater.from(requireContext())
                .inflate(R.layout.fragment_device_selection_title, null);
        final ListView listview = dialogView.findViewById(android.R.id.list);
        container = dialogView.findViewById(R.id.device_list_container);
        scanButton = dialogView.findViewById(R.id.action_cancel);
        title = dialogTitleView.findViewById(R.id.title);

        listview.setEmptyView(dialogView.findViewById(android.R.id.empty));
        listview.setAdapter(adapter = new DeviceListAdapter());

        deviceID = SharedPreferencesPackage.getDeviceID(getContext());
        deviceAddress = SharedPreferencesPackage.getDeviceAddress(getContext());
        name = SharedPreferencesPackage.getNowActivityName(getContext());

        if (name.equals("MainActivity")) {
            builder.setTitle("데이터를 불러오는 중입니다...");
            builder.setCustomTitle(dialogTitleView);
//            builder.setTitle("");
        } else {
            builder.setTitle("mine 디바이스를 찾는중입니다...");
        }
        dialog = builder.setView(dialogView).create();

        listview.setOnItemClickListener((parent, view, position, id) -> {
            stopScan();
            final ExtendedBluetoothDevice d = (ExtendedBluetoothDevice) adapter.getItem(position);
            listener.onDeviceSelected(d.device, d.name);
            // getString(R.string.cia_id)
            // "CIA_IOT"
            if (!d.name.contains("mine")) {
                Toast.makeText(getContext(), " mine 디바이스를 연결해주세요. ", Toast.LENGTH_LONG).show();
            } else {
                dialog.dismiss();
            }
            Log.e("Click", "d.device : " + d.device + "\nd.name : " + d.name);
        });

        permissionRationale = dialogView.findViewById(R.id.permission_rationale); // this is not null only on API23+

        scanButton.setOnClickListener(v -> {
            if (v.getId() == R.id.action_cancel) {
                if (scanning) {
                    dialog.cancel();
                } else {
                    startScan();
                }
            }
        });

        addBoundDevices();
        if (savedInstanceState == null)
            startScan();
        return dialog;
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);

        listener.onDialogCanceled();
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, final @NonNull String[] permissions, final @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_REQ_CODE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // We have been granted the Manifest.permission.ACCESS_FINE_LOCATION permission. Now we may proceed with scanning.
                    startScan();
                } else {
                    permissionRationale.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), R.string.no_required_permission, Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    /**
     * Scan for 5 seconds and then stop scanning when a BluetoothLE device is found then lEScanCallback
     * is activated This will perform regular scan for custom BLE Service UUID and then filter out.
     * using class ScannerServiceParser
     */
    private void startScan() {
        // Since Android 6.0 we need to obtain Manifest.permission.ACCESS_FINE_LOCATION to be able to scan for
        // Bluetooth LE devices. This is related to beacons as proximity devices.
        // On API older than Marshmallow the following code does nothing.
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // When user pressed Deny and still wants to use this functionality, show the rationale
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) && permissionRationale.getVisibility() == View.GONE) {
                permissionRationale.setVisibility(View.VISIBLE);
                return;
            }

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_REQ_CODE);
            return;
        }

        // Hide the rationale message, we don't need it anymore.
        if (permissionRationale != null)
            permissionRationale.setVisibility(View.GONE);

        adapter.clearDevices();
        scanButton.setText(R.string.scanner_action_cancel);

        container.setVisibility(View.GONE);

        final BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
        final ScanSettings settings = new ScanSettings.Builder()
                .setLegacy(false)
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).setReportDelay(1000).setUseHardwareBatchingIfSupported(false).build();
        final List<ScanFilter> filters = new ArrayList<>();
        filters.add(new ScanFilter.Builder().setServiceUuid(uuid).build());
        scanner.startScan(filters, settings, scanCallback);

        scanning = true;
        handler.postDelayed(() -> {
            if (scanning) {
                stopScan();
            }
        }, SCAN_DURATION);
    }

    /**
     * Stop scan if user tap Cancel button
     */
    private void stopScan() {
        if (scanning) {
            scanButton.setText(R.string.scanner_action_scan);

            final BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
            scanner.stopScan(scanCallback);

            scanning = false;
            dialog.setTitle(R.string.scanner_title);
            container.setVisibility(View.VISIBLE);
        }
    }

    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(final int callbackType, @NonNull final ScanResult result) {
            // do nothing
        }

        @Override
        public void onBatchScanResults(@NonNull final List<ScanResult> results) {
            adapter.update(results);
            if (!mHandler.hasMessages(Constants.MESSAGE_SCAN_SHOW_LIST) && !mHandler.hasMessages(Constants.MESSAGE_SCAN_START_MAIN)
                    && !mHandler.hasMessages(Constants.MESSAGE_SCAN_MAIN) && !mHandler.hasMessages(Constants.MESSAGE_SCAN_MAIN_UNCONN)) {
                Log.e(TAG, deviceID + "\n" + deviceAddress);
                if (deviceID.equals("") && deviceAddress.equals("")) {
                    // 첫 등록 --> showList
                    mHandler.sendEmptyMessageDelayed(Constants.MESSAGE_SCAN_SHOW_LIST, SCAN_DURATION);
                } else {
                    // 등록이 되어있으나 리스트에 검색 안됨 --> MainActivity
                    if (name.equals("MainActivity")) {
                        if (SharedPreferencesPackage.getIsConnected(getContext())) {
                            mHandler.sendEmptyMessageDelayed(Constants.MESSAGE_SCAN_MAIN, SCAN_MAIN);
                        } else {
                            Log.e(TAG, "scan : 메인에서 스켄 [ unConnected ]----start");
//                            mHandler.sendEmptyMessageDelayed(Constants.MESSAGE_SCAN_MAIN_UNCONN, SCAN_MAIN);
                            mHandler.sendEmptyMessage(Constants.MESSAGE_SCAN_MAIN_UNCONN);
                        }
                    } else {
                        mHandler.sendEmptyMessageDelayed(Constants.MESSAGE_SCAN_START_MAIN, SCAN_NULL);
                    }

                }
            }
            for (final ScanResult result : results) {
                if (result.getDevice().getAddress().equals(deviceAddress)) {
                    Log.e("connect", "address : " + result.getDevice().getAddress() + "\nname : " + result.getDevice().getName());
                    SharedPreferencesPackage.setIsConnected(getContext(), true);
                    stopScan();
                    mHandler.removeMessages(Constants.MESSAGE_SCAN_SHOW_LIST);
                    mHandler.removeMessages(Constants.MESSAGE_SCAN_START_MAIN);
                    mHandler.removeMessages(Constants.MESSAGE_SCAN_MAIN);
                    mHandler.removeMessages(Constants.MESSAGE_SCAN_MAIN_UNCONN);
                    dialog.dismiss();
                    listener.onDeviceSelected(result.getDevice(), result.getDevice().getName());
                }
            }
        }

        @Override
        public void onScanFailed(final int errorCode) {
            // should never be called
        }
    };

    private void addBoundDevices() {
        final Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
        adapter.addBondedDevices(devices);
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @SuppressLint("ResourceAsColor")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                // ---- [ Scan ] 스캔 종료 첫 등록
                case Constants.MESSAGE_SCAN_SHOW_LIST:
                    Toast.makeText(getActivity(), "mine 디바이스를 등록해 주세요.", Toast.LENGTH_SHORT).show();
                    break;
                // ---- [ Scan ] 스캔 종료 --> Main
                case Constants.MESSAGE_SCAN_START_MAIN:
                    Log.e(TAG, "onUnConnected : 감지안됨 등록은 되어있음");
                    SharedPreferencesPackage.setIsConnected(getContext(), false);
                    stopScan();
                    dialog.dismiss();
                    listener.onUnConnected();
//                    final Intent intent = new Intent(getActivity(), MainActivity.class);
//                    getActivity().startActivity(intent);
//                    getActivity().finish();

//                    stopScan();
//                    dialog.dismiss();
                    break;
                case Constants.MESSAGE_SCAN_MAIN:
                    Log.e(TAG, "scan : 메인에서 스켄 [ isConnected ]");
                    final Intent reStart = new Intent(getActivity(), MainActivity.class);
                    getActivity().startActivity(reStart);

                    stopScan();
                    dialog.dismiss();
                    break;
                case Constants.MESSAGE_SCAN_MAIN_UNCONN:
                    Log.e(TAG, "scan : 메인에서 스켄 [ unConnected ]");
                    title.setText(R.string.scanner_title);
                    title.setTextSize(20);
                    title.setTypeface(Typeface.DEFAULT_BOLD);
                    dialog.setTitle(R.string.scanner_title);
                    stopScan();
//                    stopScan();
//                    dialog.dismiss();
//                    final Intent aa = new Intent(getActivity(), MainActivity.class);
//                    getActivity().startActivity(aa);

                    break;
            }
        }
    };
}
