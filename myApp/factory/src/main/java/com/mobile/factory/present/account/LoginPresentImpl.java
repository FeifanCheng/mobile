package com.mobile.factory.present.account;

import com.mobile.factory.R;
import com.mobile.factory.helper.account.AccountHelper;
import com.mobile.util.data.DataSource;
import com.mobile.util.model.api.account.LoginModel;
import com.mobile.util.model.api.account.RegisterModel;
import com.mobile.util.model.db.entity.User;
import com.raizlabs.android.dbflow.StringUtils;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

/**
 * 没有写basePresenter基类，直接用就行
 */
public class LoginPresentImpl implements LoginPresent.Presenter {
    // 当前presenter的视图
    private LoginPresent.View my_view;

    public LoginPresent.View getMy_view() {
        return my_view;
    }

    public void setMy_view(LoginPresent.View my_view) {
        this.my_view = my_view;
    }

    /**
     * 构造器，传递进来一个view
     *
     * @param view
     */
    public LoginPresentImpl(LoginPresent.View view) {
        setMy_view(view);
        view.setPresenter(this);  // 设置好后把自己传回去
    }

    @Override
    public void requestLogin(String phone, String password) {
        start();

        // 拿到当前view
        LoginPresent.View cur_view = getMy_view();

        // 检查参数
        if (!checkInput(phone) || !checkInput(password)) {
            cur_view.LoginFail(R.string.data_account_login_invalid_parameter);
        } else {
            // 发起网络请求
            AccountHelper.login(new LoginModel(phone, password), new DataSource.Callback<User>() {
                @Override
                public void onFail(int error) {
                    // 注册失败，返回一个错误码
                    if (cur_view != null) {
                        Run.onUiAsync(new Action() {
                            @Override
                            public void call() {
                                cur_view.LoginFail(error);
                            }
                        });
                    }
                }

                @Override
                public void onSuccess(User user) {
                    // 注册成功，回送一个用户
                    // 调用注册成功即可
                    if (cur_view != null) {
                        Run.onUiAsync(new Action() {
                            @Override
                            public void call() {
                                cur_view.LoginSuccess();
                            }
                        });
                    }
                }
            });

        }
    }

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
