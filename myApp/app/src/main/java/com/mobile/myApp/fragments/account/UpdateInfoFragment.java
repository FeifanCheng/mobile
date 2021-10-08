package com.mobile.myApp.fragments.account;

import static com.mobile.myApp.tools.ImgSelector.ALBUM_REQUEST_CODE;
import static com.mobile.myApp.tools.ImgSelector.CAMERA_REQUEST_CODE;
import static com.mobile.myApp.tools.ImgSelector.TAILOR_REQUEST_CODE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.mobile.factory.present.user.UpdateInfoPresent;
import com.mobile.factory.present.user.UpdateInfoPresentImpl;
import com.mobile.myApp.R;
import com.mobile.myApp.activities.MainActivity;
import com.mobile.myApp.tools.ImgSelector;
import com.mobile.util.app.Fragment;
import com.mobile.factory.helper.network.UploadHelper;
import com.mobile.util.utils.FileUtils;
import com.mobile.util.widget.PortraitView;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.ImageView;
import net.qiujuer.genius.ui.widget.Loading;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * 更新用户信息所显示的fragment
 */
public class UpdateInfoFragment extends Fragment implements UpdateInfoPresent.View {

    private final ImgSelector imgSelector;  // 绑定一个图片选择器
    private UpdateInfoPresent.Presenter updateInfoPresent;
    private String localPortraitPath;  // 本地选择的头像地址
    private boolean isMale = true;  // 性别，默认男
    private String portraitKey; // 服务器上的key

    // 控件绑定
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.loading)
    Loading loading;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_submit)
    Button submit_button;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.im_sex)
    ImageView sex;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.im_portrait)
    PortraitView portraitView;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edit_desc)
    EditText description;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.male)
    RadioButton male;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.female)
    RadioButton female;


    public UpdateInfoFragment(ImgSelector imgSelector) {
        this.imgSelector = imgSelector;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_update_info;
    }

    // presenter初始化，返回实现类
    protected UpdateInfoPresent.Presenter initialPresenter() {
        // 传递自己就行，同时会把注册好的presenter绑定给当前view
        return new UpdateInfoPresentImpl(this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        updateInfoPresent = initialPresenter();
    }

    @Override
    public void setPresenter(UpdateInfoPresent.Presenter presenter) {
        this.updateInfoPresent = presenter;
    }


    /**
     * 调起图片选择器
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.im_portrait)
    void onPortraitClick() {
        imgSelector.onImgRequestClick(portraitView);
    }

    /**
     * 性别选中事件
     *
     * @param view
     * @param ischanged
     **/
    @SuppressLint("UseCompatLoadingForDrawables")
    @OnCheckedChanged({R.id.male, R.id.female})
    public void onRadioCheck(CompoundButton view, boolean ischanged) {
        switch (view.getId()) {
            case R.id.male:
                if (ischanged) {
                    isMale = true;
                    sex.setImageDrawable(getResources().getDrawable(R.drawable.ic_man));
                }
                break;
            case R.id.female:
                if (ischanged) {
                    isMale = false;
                    sex.setImageDrawable(getResources().getDrawable(R.drawable.ic_womam));
                }
                break;
            default:
                break;
        }
    }

    /**
     * 提交更改
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        String desc = description.getText().toString();
        // 先把图片上传了
        try {
            portraitKey = UploadHelper.uploadImage(localPortraitPath, getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("aaa", localPortraitPath);
        // 发起请求更新数据库
        updateInfoPresent.requestUpdate(localPortraitPath, desc, isMale);
    }

    @Override
    public void UpdateInfoSuccess() {
        Toast.makeText(getContext(), "Update succeed!", Toast.LENGTH_SHORT).show();

        // 跳转到main并结束当前activity
        MainActivity.show(Objects.requireNonNull(getContext()));
        Objects.requireNonNull(getActivity()).finish();
    }

    @Override
    public void UpdateInfoFail(int error) {
        // TODO：先这样
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();

        // 停止loading并恢复输入
        loading.stop();
        sex.setEnabled(true);
        portraitView.setEnabled(true);
        description.setEnabled(true);
        submit_button.setEnabled(true);
    }

    @Override
    public void loading() {
        // TODO：先这样
        Toast.makeText(getContext(), "loading", Toast.LENGTH_SHORT).show();

        // 开始loading并禁止输入
        loading.start();
        sex.setEnabled(false);
        portraitView.setEnabled(false);
        description.setEnabled(false);
        submit_button.setEnabled(false);
    }

    /**
     * 所有回调结果返回的逻辑在这里写
     *
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
                String path = FileUtils.uriToFile(imgSelector.getImageUri(), getContext()).getAbsolutePath();
                // 设置头像和本地地址
                portraitView.setImageURI(imgSelector.getImageUri());
                localPortraitPath = path;
                Log.e(ImgSelector.TAG, localPortraitPath);

//                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//                    //照片裁剪
//                    imgSelector.openCrop(imgSelector.getImageUri());
//                } else {
//                    Toast.makeText(getContext(), "No SDCard", Toast.LENGTH_SHORT).show();
//                }
            } else if (requestCode == ALBUM_REQUEST_CODE) {
                if (data != null && data.getData() != null) {
                    Log.e(ImgSelector.TAG, "相册回调成功");
                    String path = FileUtils.uriToFile(data.getData(), getContext()).getAbsolutePath();
                    // 设置头像和本地地址
                    portraitView.setImageURI(data.getData());
                    localPortraitPath = path;
                    Log.e(ImgSelector.TAG, localPortraitPath);
                }
            } else {
                Log.e(ImgSelector.TAG, "图片剪裁回调");
                Uri uri = Uri.fromFile(imgSelector.getImgFile());
                portraitView.setImageURI(uri); // 设置头像
            }
        } else {
            // TODO: 后面如果有这个方法的逻辑写在这里
            Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT).show();
        }
    }

}
