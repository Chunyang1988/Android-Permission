package com.mobile.android.permission;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xiu8.log.XLog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onClick(View view) {

        CPermission.requestPermission(this, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {
                XLog.d("权限：获取到");
            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {
                XLog.d("权限：没有获取到");
            }
        }, Manifest.permission.CAMERA);

    }
}
