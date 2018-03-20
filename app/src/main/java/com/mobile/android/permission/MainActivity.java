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


//        RxPermissions rxPermissions = new RxPermissions(this);
//        rxPermissions.request(Manifest.permission.CAMERA).subscribe(new Consumer<Boolean>() {
//            @Override
//            public void accept(Boolean aBoolean) throws Exception {
//                XLog.d("权限：" + aBoolean);
//            }
//        });


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
//                XLog.d("权限：shouldShowRequestPermissionRationale true");
//            } else {
//                XLog.d("权限：shouldShowRequestPermissionRationale");
//
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
//            }
//
//        }

//        if (!EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
//        EasyPermissions.requestPermissions(this, "必要的权限", 0, Manifest.permission.CAMERA);
//        }

//        XPermission.requestPermission(this, new PermissionListener() {
//            @Override
//            public void permissionGranted(@NonNull String[] permission) {
//                XLog.d("权限：permissionGranted");
////                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
////                intent.putExtra(CameraActivity.PATH, Environment.getExternalStorageDirectory().getPath());
////                intent.putExtra(CameraActivity.OPEN_PHOTO_PREVIEW, true);
////                intent.putExtra(CameraActivity.USE_FRONT_CAMERA, false);
////                startActivity(intent);
//            }
//
//            @Override
//            public void permissionDenied(@NonNull String[] permission) {
//                XLog.d("权限：permissionDenied");
//            }
//        }, Manifest.permission.READ_CONTACTS);
    }


    @Override
    protected void onResume() {
        super.onResume();
//        if (XPermissions.hasPermission(this, Manifest.permission.CAMERA)) {
//            XLog.d("含有权限");
//            return;
//        }

    }

    public void onClick(View view) {

        XPermission.requestPermission(this, new PermissionListener() {
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
