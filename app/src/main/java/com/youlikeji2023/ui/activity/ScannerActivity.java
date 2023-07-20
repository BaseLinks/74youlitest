package com.youlikeji2023.ui.activity;

import android.content.Intent;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;

import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.youlikeji2023.R;

public class ScannerActivity extends CaptureActivity implements View.OnClickListener {
    private CaptureManager captureManager;
    private DecoratedBarcodeView barcodeView;

    private AppCompatImageView backIv;

    private CameraManager cameraManager;

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_capture);

        barcodeView = findViewById(R.id.viewfinder_view);
        captureManager = new CaptureManager(this, barcodeView);
        captureManager.initializeFromIntent(getIntent(), savedInstanceState);
        captureManager.decode();
        initView();
    }

    private void initView() {
        backIv = findViewById(R.id.backIv);
        backIv.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.backIv) {
            finish();

        }


    }


    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        captureManager.onSaveInstanceState(outState);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
