package com.mobile.factory.present.account;

public interface UpdateInfoPresent {
    interface Presenter {
        // 发起更新
        void requestUpdate(String localPortraitPath, String description, boolean isMale);

        // 参数验证
        boolean checkInput(String input);

        void start();

        void finish();

    }

    interface View {
        // 设置一个presenter
        void setPresenter(UpdateInfoPresent.Presenter presenter);

        void UpdateInfoSuccess();

        void UpdateInfoFail(int error);

        void loading();
    }
}
