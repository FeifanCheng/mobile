package com.mobile.myapp.fragments.account;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.mobile.util.StaticData.AccountData;
import com.mobile.factory.present.account.LoginPresent;
import com.mobile.factory.present.account.LoginPresentImpl;
import com.mobile.myapp.R;
import com.mobile.myapp.activities.MainActivity;
import com.mobile.util.app.Fragment;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.ImageView;
import net.qiujuer.genius.ui.widget.Loading;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录展示的页面，没有做PresenterFragment抽离
 */
public class LoginFragment extends Fragment implements LoginPresent.View {
    private LoginPresent.Presenter loginPresent;
    private ViewTransfer viewTransfer;  // 用于界面跳转

    // 控件绑定
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edit_phone)
    net.qiujuer.genius.ui.widget.EditText input_phone;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edit_password)
    net.qiujuer.genius.ui.widget.EditText input_password;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.loading)
    Loading loading;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_submit)
    Button submit_button;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.go_register)
    FrameLayout go_register_button;

    // presenter初始化，返回实现类
    protected LoginPresent.Presenter initialPresenter(){
        // 传递自己就行，同时会把注册好的presenter绑定给当前view
        return new LoginPresentImpl(this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // 初始化跳转和presenter
        viewTransfer = (ViewTransfer) context;
        loginPresent = initialPresenter();
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void setPresenter(LoginPresent.Presenter presenter) {
        this.loginPresent = presenter;
    }

    /**
     * 跳转注册
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.go_register)
    void onGoLoginClick(){
        // 跳转页面
        viewTransfer.transfer();
    }

    /**
     * 提交登录
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn_submit)
    void onSubmitClick(){
        String phone = input_phone.getText().toString();
        String password = input_password.getText().toString();
        loginPresent.requestLogin(phone, password);
    }

    @Override
    public void LoginSuccess() {
        Toast.makeText(getContext(), "Login succeed!", Toast.LENGTH_SHORT).show();

        // 跳转到main并结束当前activity
        MainActivity.show(Objects.requireNonNull(getContext()));
        Objects.requireNonNull(getActivity()).finish();
    }

    @Override
    public void LoginFail(int error) {
        // TODO：先这样
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();

        // 停止loading并恢复输入
        loading.stop();
        input_phone.setEnabled(true);
        input_password.setEnabled(true);
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
        submit_button.setEnabled(false);
    }


}
