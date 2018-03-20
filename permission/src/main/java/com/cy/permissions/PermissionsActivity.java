package com.cy.permissions;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by chunyang on 2018/3/14.
 */

public class PermissionsActivity extends AppCompatActivity {


    static final String TAG = "PermissionsActivity";

    private static final int PERMISSION_REQUEST_CODE = 999;
    private static final String EXTRA_PERMISSION = "permissions";
    private static final String EXTRA_KEY = "key";
    //    private static final String EXTRA_SHOW_TIP = "showTip";
    //    private static final String EXTRA_TIP = "tip";
    private String[] permissions;
    private String key;
    private boolean isRequireCheck;

    private final String defaultTitle = "帮助";
    private final String defaultContent = "当前应用缺少必要权限。\n \n 请点击 \"设置\"-\"权限\"-打开所需权限。";
    private final String defaultCancel = "取消";
    private final String defaultEnsure = "设置";

    static Intent getStartIntent(Context context, String[] permission, String key) {
        Intent intent = new Intent(context, PermissionsActivity.class);
        intent.putExtra(EXTRA_PERMISSION, permission);
        intent.putExtra(EXTRA_KEY, key);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null || !getIntent().hasExtra(EXTRA_PERMISSION)) {
            finish();
            return;
        }
        isRequireCheck = true;
        permissions = getIntent().getStringArrayExtra(EXTRA_PERMISSION);
        key = getIntent().getStringExtra(EXTRA_KEY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isRequireCheck) {
            if (CPermission.hasPermission(this, permissions)) {
                permissionsGranted();
                Log.w(TAG, "onResume: 已经授权过了");
            } else {
                if (CPermission.shouldShowPermission(this, permissions)) {
                    Log.w(TAG, "onResume: 用户拒绝了授权");
                    showMissingPermissionDialog();
                } else {
                    Log.w(TAG, "onResume: 开始请求授权");
                    requestPermissions(permissions);
                }
            }
            isRequireCheck = false;
        } else {
            Log.w(TAG, "onResume: 重置数据");
            isRequireCheck = true;
        }
    }

    // 显示缺失权限提示s
    private void showMissingPermissionDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(PermissionsActivity.this);

        builder.setTitle(defaultTitle);
        builder.setMessage(defaultContent);

        builder.setNegativeButton(defaultCancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                permissionsDenied();
            }
        });

        builder.setPositiveButton(defaultEnsure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CPermission.gotoSetting(PermissionsActivity.this);
            }
        });

        builder.setCancelable(false);
        builder.show();
    }

    // 请求权限兼容低版本
    private void requestPermissions(String[] permissions) {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.w(TAG, "requestCode:" + requestCode);
        //部分厂商手机系统返回授权成功时，厂商可以拒绝权限，所以要用PermissionChecker二次判断
        if (requestCode == PERMISSION_REQUEST_CODE &&
                CPermission.isGrantedResult(grantResults) &&
                CPermission.hasPermission(this, permissions)) {
            permissionsGranted();
            Log.w(TAG, "onRequestPermissionsResult: 获取到权限");
        } else { //不需要提示用户
            showMissingPermissionDialog();
            Log.w(TAG, "onRequestPermissionsResult: 没有获取到权限");
        }
    }


    private void permissionsDenied() {
        PermissionListener listener = CPermission.fetchListener(key);
        if (listener != null) {
            listener.permissionDenied(permissions);
        }
        finish();
    }

    // 全部权限均已获取
    private void permissionsGranted() {
        PermissionListener listener = CPermission.fetchListener(key);
        if (listener != null) {
            listener.permissionGranted(permissions);
        }
        finish();
    }
}
