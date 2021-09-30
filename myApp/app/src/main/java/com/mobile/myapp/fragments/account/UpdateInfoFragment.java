package com.mobile.myapp.fragments.account;

import static com.mobile.myapp.tools.ImgSelector.ALBUM_REQUEST_CODE;
import static com.mobile.myapp.tools.ImgSelector.CAMERA_REQUEST_CODE;
import static com.mobile.myapp.tools.ImgSelector.TAILOR_REQUEST_CODE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.mobile.myapp.R;
import com.mobile.myapp.tools.ImgSelector;
import com.mobile.util.app.Fragment;
import com.mobile.factory.helper.network.UploadHelper;
import com.mobile.util.utils.FileUtils;
import com.mobile.util.widget.PortraitView;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 更新用户信息所显示的fragment
 */
public class UpdateInfoFragment extends Fragment {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.im_portrait)
    PortraitView mPortrait;

    private final ImgSelector imgSelector;

    public UpdateInfoFragment(ImgSelector imgSelector) {
        this.imgSelector = imgSelector;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_update_info;
    }

    /**
     * 调起图片选择器
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.im_portrait)
    void onPortraitClick(){
        imgSelector.onImgRequestClick(mPortrait);
    }

    /**
     * 所有回调结果返回的逻辑在这里写
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(ImgSelector.TAG, String.valueOf(resultCode));
        // 如果是处理图片的回调
        if (resultCode == -1 && (requestCode == CAMERA_REQUEST_CODE ||
                requestCode == ALBUM_REQUEST_CODE || requestCode == TAILOR_REQUEST_CODE)) {
            //回调成功
            if (requestCode == CAMERA_REQUEST_CODE) {
                Log.e(ImgSelector.TAG, "相机回调");
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    //照片裁剪
                    imgSelector.openCrop(imgSelector.getImageUri());
                } else {
                    Toast.makeText(getContext(), "No SDCard", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == ALBUM_REQUEST_CODE) {
                Log.e(ImgSelector.TAG, "相册回调");
                if (data != null && data.getData() != null) {
                    Log.e(ImgSelector.TAG, "相册回调成功");
                    // TODO: test上传到服务器
                    String path = FileUtils.uriToFile(data.getData(), getContext()).getAbsolutePath();
                    try {
                        UploadHelper.uploadImage(path, getContext());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mPortrait.setImageURI(data.getData()); // 设置头像
//                    imgSelector.openCrop(data.getData()); // 剪裁
                }
            } else {
                Log.e(ImgSelector.TAG, "图片剪裁回调");
                Uri uri = Uri.fromFile(imgSelector.getImgFile());
                mPortrait.setImageURI(uri); // 设置头像

                // TODO: 上传到服务器
                File file = imgSelector.getImgFile();
            }
        } else {
            // TODO: 后面如果有这个方法的逻辑写在这里
            Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT).show();
        }
    }
}
