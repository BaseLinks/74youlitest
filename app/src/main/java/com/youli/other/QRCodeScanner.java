package com.youli.other;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.youli.ui.activity.HomeActivity;
import com.youli.ui.fragment.HomeFragment;

public class QRCodeScanner {
    private Context context;
    private HomeActivity homeActivity;
//    HomeFragment homeFragment = HomeFragment.newInstance();

    public QRCodeScanner(Context context) {
        this.context = context;
    }

    public void startScan(Activity activity) {
        IntentIntegrator integrator = new IntentIntegrator(activity);

//        integrator.setCaptureActivity(MainActivity2.class);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
        // 设置要扫描的条码类型，ONE_D_CODE_TYPES：一维码，QR_CODE_TYPES-二维码
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        //  integrator.setCaptureActivity(CaptureAct.class);
        integrator.setPrompt("扫描条码");

//        integrator.setCaptureActivity(MainActivity2.class)
        integrator.setOrientationLocked(false);
        integrator.setCameraId(0);  // 使用默认的相机
        integrator.setBeepEnabled(false); // 扫到码后播放提示音
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
//        requestDataListener.requestData("oooooo");
        homeActivity = (HomeActivity) activity;
        homeActivity.setRequestDataListener(new requestData() {
            @Override
            public void requestData(String data) {
                Toast.makeText(activity, data, Toast.LENGTH_SHORT).show();
                final Uri uri= Uri.parse(data);
                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                (activity).startActivity(intent);
            }
        });
    }

    public void handleScanResult(int requestCode, int resultCode, Intent intent) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (result != null && result.getContents() != null) {
            String scannedData = result.getContents();
//            showToast(scannedData);
//            requestDataListener.requestData(scannedData);
//            homeFragment.requestData(scannedData);
        }

    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    public requestData requestDataListener;

    public void setRequestDataListener(requestData requestDataListener) {
        this.requestDataListener = requestDataListener;
    }

    public interface requestData {
        void requestData(String data);
    }
}


