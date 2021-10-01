package com.mobile.myApp.tools;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.os.EnvironmentCompat;

import com.mobile.myApp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 *  图像选择器，传入调起时对应的activity和context
 *  可以实现剪切，选择和拍摄
 *  TODO：模拟器无法调取相机，切编辑图片后之后保存不上，后续用真机调试，逻辑没问题
 *  图片每次只能选一张
 *  onResult需要根据需求在activity中进行复写
 *  fragment调这个东西的时候必须从activity里新建好再传过去，不然会报空指针 ！！！详见UserInfoActivity里的用法
 */
public class ImgSelector {
    private Context context;
    private Activity activity;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.main_portrait)
    CircleImageView portraitView;

    public final static int PHOTO_REQUEST_CAMERA = 10;//相机权限请求
    public  final static int PHOTO_REQUEST_ALBUM = 20;//相册权限请求
    public  final static int CAMERA_REQUEST_CODE = 100;//相机跳转code
    public final static int ALBUM_REQUEST_CODE = 200;//相册跳转code
    public  final static int TAILOR_REQUEST_CODE = 300;//图片剪裁code
    private Uri imageUri;//需要上传的图片的Uri
    private File file;//需要上传的图片的文件
    public static String TAG = "ImgSelector";
    public static boolean isAndroidQ = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
    private final static String SAVE_AVATAR_NAME = "testImgSelector.png";//需要上传的图片的文件名

    /**
     * 传入调起时对应的activity和context
     * @param context
     * @param activity
     */
    public ImgSelector(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
    }

    public Uri getImageUri() {
        return imageUri;
    }


    public File getImgFile(){
        return file;
    }

    /**
     * 弹出选择拍照或者相册的窗口
     *
     * @param view 一个按钮对应的onclick传入这个方法
     */
    public void onImgRequestClick(View view) {
        List<String> names = new ArrayList<>();
        names.add("Take Picture");
        names.add("Select from Album");
        showDialog(new SelectDialog.SelectDialogListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // 直接调起相机
                        if ((ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                                == PackageManager.PERMISSION_GRANTED)
                                && (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                == PackageManager.PERMISSION_GRANTED)
                                && (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                                == PackageManager.PERMISSION_GRANTED)) {
                            //权限都齐的情况下，跳转相机
                            openCamera();
                        } else {
                            if (activity != null) {
                                //请求权限
                                ActivityCompat.requestPermissions(activity, new String[]{
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE
                                }, PHOTO_REQUEST_CAMERA);
                            }
                        }
                        break;
                    case 1:
                        //相册
                        if ((ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                == PackageManager.PERMISSION_GRANTED)
                                && (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                                == PackageManager.PERMISSION_GRANTED)) {
                            //权限都齐的情况下，跳转相册
                            openAlbum();
                        } else {
                            if (activity != null) {
                                //请求权限
                                ActivityCompat.requestPermissions(activity, new String[]{
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE
                                }, PHOTO_REQUEST_ALBUM);
                            }
                        }
                        break;
                    default:
                        break;
                }

            }
        }, names);
    }

    /**
     * 显示弹出窗口，选择拍照或者相册
     */
    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(activity, R.style
                .transparentFrameWindowStyle,
                listener, names);
        if (!this.activity.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

    /**
     * 打开相机
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @SuppressLint("QueryPermissionsNeeded")
    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断是否有相机
        if (activity != null && context != null && intent.resolveActivity(activity.getPackageManager()) != null) {
            File file;
            Uri uri = null;
            if (isAndroidQ) {
                //适配Android10
                uri = createImageUri(context);
            } else {
                //Android10以下
                file = createImageFile(context);
                if (file != null) {
                    //Android10以下
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //适配Android7.0文件权限
                        uri = FileProvider.getUriForFile(context, "com.example.camera.test", file);
                    } else {
                        uri = Uri.fromFile(file);
                    }
                }
            }
            imageUri = uri;
            Log.e(TAG, "相机保存的图片Uri：" + imageUri);
            if (uri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                activity.startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        } else {
            Log.e(TAG, "没有相机");
        }
    }

    /**
     * 跳转相册
     */
    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent, ALBUM_REQUEST_CODE);
    }

    /**
     * 跳转裁剪
     */
    public void openCrop(Uri uri) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && context != null) {
            file = new File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/0"), SAVE_AVATAR_NAME);
            Log.e(TAG, "裁剪图片存放路径：" + file.getAbsolutePath());
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 520);
        intent.putExtra("outputY", 520);
        //适配Android10，存放图片路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        // 图片格式
        intent.putExtra("outputFormat", "PNG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        activity.startActivityForResult(intent, TAILOR_REQUEST_CODE);
    }

    /**
     * Android10创建图片uri，用来保存拍照后的图片
     *
     * @return uri
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private Uri createImageUri(@NonNull Context context) {
        String status = Environment.getExternalStorageState();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, SAVE_AVATAR_NAME);
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/*");
        contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/0/");
        //判断是否有SD卡
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        } else {
            return context.getContentResolver().insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, contentValues);
        }
    }

    /**
     * Android10以下创建图片file，用来保存拍照后的照片
     *
     * @return file
     */
    private File createImageFile(@NonNull Context context) {
        File file = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (file != null && !file.exists()) {
            if (file.mkdir()) {
                Log.e(TAG, "文件夹创建成功");
            } else {
                Log.e(TAG, "file为空或者文件夹创建失败");
            }
        }
        File tempFile = new File(file, SAVE_AVATAR_NAME);
        Log.e(TAG, "临时文件路径：" + tempFile.getAbsolutePath());
        if (!Environment.MEDIA_MOUNTED.equals(EnvironmentCompat.getStorageState(tempFile))) {
            return null;
        }
        return tempFile;
    }

}
