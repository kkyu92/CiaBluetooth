package com.example.ciabluetooth.activity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.example.ciabluetooth.R;
import com.example.ciabluetooth.databinding.ActivityDrawerBinding;
import com.example.ciabluetooth.util.FileDownloader;

import java.io.File;
import java.io.IOException;

import static com.example.ciabluetooth.Constants.PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE;

public class DrawerActivity extends BaseActivity {
    private String TAG = this.getClass().getSimpleName();
    private ActivityDrawerBinding mBinding;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(DrawerActivity.this, R.layout.activity_drawer);
        mBinding.setView(DrawerActivity.this);

        Intent getIntent = getIntent();
        title = getIntent.getStringExtra("title");
        mBinding.title.setText(title);
    }

    @Override
    protected void onResume() {
        super.onResume();

        switch (title) {
            case "품질보증 및 약관":
                mBinding.faq.setVisibility(View.GONE);
                mBinding.as.setVisibility(View.GONE);
                mBinding.pdf.setVisibility(View.GONE);
                break;
            case "주의사항 및 FAQ":
                mBinding.terms.setVisibility(View.GONE);
                mBinding.as.setVisibility(View.GONE);
                mBinding.pdf.setVisibility(View.GONE);
                break;
            case "AS 서비스":
                mBinding.terms.setVisibility(View.GONE);
                mBinding.faq.setVisibility(View.GONE);
                mBinding.pdf.setVisibility(View.GONE);
                break;
            case "매뉴얼 다운로드":
                mBinding.terms.setVisibility(View.GONE);
                mBinding.as.setVisibility(View.GONE);
                mBinding.faq.setVisibility(View.GONE);
//                downloadPDF("http://maven.Apache.org/maven-1.x/maven");
//                download();
//                view();
                break;
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;
            case R.id.download:
                Toast.makeText(this, "'CiaManual' 다운로드", Toast.LENGTH_SHORT).show();
                StartDownlaod();
                break;
            case R.id.view:
                String URL = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL)));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void DownloadManual() {
        String URL = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";
        String tempTitle = "CiaManual";

        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(URL));

        request.setTitle(tempTitle);
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, tempTitle + ".pdf");

        request.setMimeType("application/pdf");
        request.allowScanningByMediaScanner();
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        downloadManager.enqueue(request);
    }

    public void StartDownlaod() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            // 권한 없음
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            // 권한 있음
            DownloadManual();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    DownloadManual();
                } else {
                    Toast.makeText(this, "권한이 없어 다운로드 할 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
        }
    }
}

