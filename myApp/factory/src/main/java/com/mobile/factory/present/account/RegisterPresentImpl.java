package com.mobile.factory.present.account;

import com.mobile.util.helper.account.AccountHelper;
import com.mobile.util.model.api.RegisterModel;
import com.raizlabs.android.dbflow.StringUtils;

/**
 * 没有写basePresenter基类，直接用就行
 */
public class RegisterPresentImpl implements RegisterPresent.Presenter {
    // 当前presenter的视图
    private RegisterPresent.View my_view;

    public RegisterPresent.View getMy_view() {
        return my_view;
    }

    public void setMy_view(RegisterPresent.View my_view) {
        this.my_view = my_view;
    }

    /**
     * 构造器，传递进来一个view
     *
     * @param view
     */
    public RegisterPresentImpl(RegisterPresent.View view) {
        setMy_view(view);
        view.setPresenter(this); // 把presenter设置回去
    }

    @Override
    public void requestRegister(String phone, String name, String password) {
        start();

        // 检查参数
        if (!checkInput(phone)) {

        } else if (!checkInput(password) || password.length() < 6) {

        } else if (!checkInput(name)) {

        } else {
            // 发起网络请求
            AccountHelper.register(new RegisterModel(phone, password, name));

        }
    }

    /**
     * 检查参数是否正确(不为空就行了，没写持久化那个正则)
     *
     * @param input
     */
    @Override
    public boolean checkInput(String input) {
        return !StringUtils.isNullOrEmpty(input);
    }

    @Override
    public void start() {
        // 如果不为空，显示loading
        if (my_view != null) {
            my_view.loading();
        }
    }

    @Override
    public void finish() {
        // 设置presenter和当前view为空
        if (my_view != null) {
            my_view.setPresenter(null);
        }
        setMy_view(null);
    }
}
