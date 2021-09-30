package com.mobile.factory.present.account;

/**
 * 没有写basePresenter基类，直接用就行
 */
public class LoginPresentImpl implements LoginPresent.Presenter{
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
     * @param view
     */
    public LoginPresentImpl(LoginPresent.View view){
        setMy_view(view);
        view.setPresenter(this);  // 设置好后把自己传回去
    }

    @Override
    public void requestLogin(String phone, String password) {

    }

    @Override
    public void checkInput(String input) {

    }

    @Override
    public void start() {
        // 如果不为空，显示loading
        if(my_view != null){
            my_view.loading();
        }
    }

    @Override
    public void finish() {
        // 设置presenter和当前view为空
        if(my_view != null){
            my_view.setPresenter(null);
        }
        setMy_view(null);
    }
}
