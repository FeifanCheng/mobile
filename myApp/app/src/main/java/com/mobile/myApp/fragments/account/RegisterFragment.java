package com.mobile.myApp.fragments.account;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.mobile.factory.present.account.RegisterPresent;
import com.mobile.factory.present.account.RegisterPresentImpl;
import com.mobile.myApp.R;
import com.mobile.myApp.activities.MainActivity;
import com.mobile.util.app.Fragment;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册展示的页面，没有做PresenterFragment抽离
 */
public class RegisterFragment extends Fragment implements RegisterPresent.View {
    private ViewTransfer viewTransfer;  // 用于界面跳转
    private RegisterPresent.Presenter registerPresent;  // 用于页面展示和网络请求

    // 控件绑定
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edit_phone)
    net.qiujuer.genius.ui.widget.EditText input_phone;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edit_password)
    net.qiujuer.genius.ui.widget.EditText input_password;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edit_name)
    net.qiujuer.genius.ui.widget.EditText input_name;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.loading)
    Loading loading;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_submit)
    Button submit_button;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.go_login)
    FrameLayout go_login_button;


    // presenter初始化，返回实现类
    protected RegisterPresent.Presenter initialPresenter(){
        // 传递自己就行，同时会把注册好的presenter绑定给当前view
        return new RegisterPresentImpl(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // 初始化跳转和presenter
        viewTransfer = (ViewTransfer) context;
        registerPresent = initialPresenter();
    }

    /**
     * 提交注册
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn_submit)
    void onSubmitClick(){
        String phone = input_phone.getText().toString();
        String name = input_name.getText().toString();
        String password = input_password.getText().toString();
        registerPresent.requestRegister(phone, name, password);
    }

    /**
     * 跳转登录
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.go_login)
    void onGoLoginClick(){
        // 跳转页面
        viewTransfer.transfer();
    }

    @Override
    public void registerSuccess() {
        Toast.makeText(getContext(), "Register succeed!", Toast.LENGTH_SHORT).show();

        // 跳转到main并结束当前activity
        MainActivity.show(Objects.requireNonNull(getContext()));
        Objects.requireNonNull(getActivity()).finish();
    }

    @Override
    public void registerFail(int error) {
        // TODO：先这样
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();

        // 停止loading并恢复输入
        loading.stop();
        input_phone.setEnabled(true);
        input_password.setEnabled(true);
        input_name.setEnabled(true);
        submit_button.setEnabled(true);
    }

    @Override
    public void loading() {
        // TODO：先这样
        Toast.makeText(getContext(), "loading", Toast.LENGTH_SHORT).show();

        // 开始loading并禁止输入
        loading.start();
        input_phone.setEnabled(false);
        input_password.setEnabled(false);
        input_name.setEnabled(false);
        submit_button.setEnabled(false);
    }

    @Override
    public void setPresenter(RegisterPresent.Presenter presenter) {
        this.registerPresent = presenter;
    }
}
