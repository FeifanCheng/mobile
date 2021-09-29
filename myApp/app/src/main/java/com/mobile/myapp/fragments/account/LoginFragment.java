package com.mobile.myapp.fragments.account;

import com.mobile.myapp.R;
import com.mobile.util.app.Fragment;

/**
 * 登录展示的页面，没有做PresenterFragment抽离
 */
public class LoginFragment extends Fragment {
    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_login;
    }
}
