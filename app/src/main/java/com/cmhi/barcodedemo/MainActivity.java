package com.cmhi.barcodedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.tbruyelle.rxpermissions3.RxPermissions;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class MainActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler, View.OnClickListener {

    private ZBarScannerView mScannerView;

    private Button scanBtn;

    private EditText scanEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanBtn = (Button) findViewById(R.id.scan_btn);
        scanBtn.setOnClickListener(this);
        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.scan_fl);
        mScannerView = new ZBarScannerView(this);
        contentFrame.addView(mScannerView);

        scanEt = (EditText) findViewById(R.id.scan_content_et);

        final RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA)

                .subscribe(granted ->{
                    if (!granted){
                        finish();
                    }
                });
    }

    @Override
    public void handleResult(Result result) {
        mScannerView.stopCamera();
        String scanStr = scanEt.getText() + result.getContents() + ",";
        scanEt.setText(scanStr);
    }

    @Override
    public void onClick(View v) {
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }
}