package com.mobile.factory.present.account;

/**
 * 这里没有用basic那个抽离，改了结构的
 */
public interface LoginPresent {
    interface Presenter {
        // 发起登录
        void requestLogin(String phone, String password);

        // 参数验证
        void checkInput(String input);

        void start();

        void finish();

    }

    interface View {
        // 设置一个presenter
        void setPresenter(Presenter presenter);

        void LoginSuccess();

        void LoginFail(int error);

        void loading();
    }
}
