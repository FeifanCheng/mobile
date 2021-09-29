package com.mobile.factory.present.account;

/**
 * 这里没有用basic那个抽离，改了结构的
 */
public interface RegisterPresent {

    interface Presenter {
        // 发起注册
        void requestRegister(String phone, String name, String password);

        // 参数验证
        boolean checkInput(String input);

        void start();

        void finish();

    }

    interface View {
        // 设置一个presenter
        void setPresenter(Presenter presenter);

        void registerSuccess();

        void registerFail(int error);

        void loading();
    }


}
