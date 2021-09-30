package com.mobile.myapp.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mobile.myapp.R;
import com.mobile.util.app.Activity;

/**
 * app启动的时候调起的activity
 */
public class OpenAppActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
            MainActivity.show(this);
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
