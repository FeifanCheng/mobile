package com.mobile.myApp.activities;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mobile.util.StaticData.AccountData;
import com.mobile.myApp.R;
import com.mobile.util.app.Activity;

/**
 * app启动的时候调起的activity
 * TODO：没有做推送和设备绑定，后面配好了补一下
 */
public class OpenAppActivity extends Activity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.open_app;
    }


    // TODO: 真机测试一下网络权限，虚拟机上生效了
    @Override
    protected void onResume() {
        super.onResume();

        if ((ContextCompat.checkSelfPermission(OpenAppActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(OpenAppActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(OpenAppActivity.this, Manifest.permission.INTERNET)
                == PackageManager.PERMISSION_GRANTED)) {
            // 如果已经登录了，直接进主页面
            if (AccountData.isLogin()) {
                MainActivity.show(this);
            } else {
                AccountActivity.show(this);
            }
            finish();
        } else {
            //请求权限
            ActivityCompat.requestPermissions(OpenAppActivity.this, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO
            }, 1);
        }
    }


}
