package com.mobile.myapp.activities;

import android.content.Context;
import android.content.Intent;

import com.mobile.myapp.R;
import com.mobile.myapp.fragments.account.LoginFragment;
import com.mobile.myapp.fragments.account.RegisterFragment;
import com.mobile.myapp.fragments.account.ViewTransfer;
import com.mobile.util.app.Activity;
import com.mobile.util.app.Fragment;

import net.qiujuer.genius.ui.widget.ImageView;

import butterknife.BindView;

public class AccountActivity extends Activity implements ViewTransfer {
    private Fragment curFragment;
    private Fragment loginFragment;
    private Fragment registerFragment;

    @BindView(R.id.im_bg)
    ImageView mBg;

    /**
     * 账户Activity显示的入口
     *
     * @param context Context
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.account_activity;
    }


    @Override
    protected void initialWidget() {
        super.initialWidget();

        // 初始化Fragment，默认是登录
        curFragment = loginFragment = new LoginFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, curFragment)
                .commit();

    }

    /**
     * 界面跳转
     */
    @Override
    public void transfer() {
        Fragment fragment;
        if (curFragment == loginFragment) {
            if (registerFragment == null) {
                //默认情况下为null，
                //第一次之后就不为null了
                registerFragment = new RegisterFragment();
            }
            fragment = registerFragment;
        } else {
            // 因为默认请求下mLoginFragment已经赋值，无须判断null
            fragment = loginFragment;
        }

        // 重新赋值当前正在显示的Fragment
        curFragment = fragment;
        // 切换显示ø
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.lay_container, curFragment)
                .commit();
    }

}
