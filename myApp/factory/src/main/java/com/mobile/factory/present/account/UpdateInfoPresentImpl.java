package com.mobile.factory.present.account;

public class UpdateInfoPresentImpl implements UpdateInfoPresent.Presenter{
    // 当前presenter的视图
    private UpdateInfoPresent.View my_view;

    public UpdateInfoPresent.View getMy_view() {
        return my_view;
    }

    public void setMy_view(UpdateInfoPresent.View my_view) {
        this.my_view = my_view;
    }

    /**
     * 构造器，传递进来一个view
     *
     * @param view
     */
    public UpdateInfoPresentImpl(UpdateInfoPresent.View view) {
        setMy_view(view);
        view.setPresenter(this); // 把presenter设置回去
    }

    @Override
    public void requestUpdate(String localPortraitPath, String description, boolean isMale) {

    }

    @Override
    public boolean checkInput(String input) {
        return false;
    }

    @Override
    public void start() {

    }

    @Override
    public void finish() {

    }
}
