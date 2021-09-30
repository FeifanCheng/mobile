package com.mobile.factory.present.account;

import com.mobile.factory.R;
import com.mobile.util.data.DataSource;
import com.mobile.factory.helper.account.AccountHelper;
import com.mobile.util.model.api.account.RegisterModel;
import com.mobile.util.model.db.User;
import com.raizlabs.android.dbflow.StringUtils;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

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

        // 拿到当前view
        RegisterPresent.View cur_view = getMy_view();

        // 检查参数
        if (!checkInput(phone)) {
            cur_view.registerFail(R.string.data_account_register_invalid_parameter_mobile);
        } else if (!checkInput(password) || password.length() < 6) {
            cur_view.registerFail(R.string.data_account_register_invalid_parameter_password);
        } else if (!checkInput(name)) {
            cur_view.registerFail(R.string.data_account_register_invalid_parameter_name);
        } else {
            // 发起网络请求
            AccountHelper.register(new RegisterModel(phone, password, name), new DataSource.Callback<User>() {
                @Override
                public void onFail(int error) {
                    // 注册失败，返回一个错误码
                    if (cur_view != null) {
                        Run.onUiAsync(new Action() {
                            @Override
                            public void call() {
                                cur_view.registerFail(error);
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
                                cur_view.registerSuccess();
                            }
                        });
                    }
                }
            });

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
