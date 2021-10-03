package com.mobile.factory.present.user;

import com.mobile.factory.R;
import com.mobile.factory.helper.user.UserHelper;
import com.mobile.util.data.DataSource;
import com.mobile.util.model.api.user.UserUpdateInfoModel;
import com.mobile.util.model.db.entity.User;
import com.mobile.util.model.db.identity.UserIdentity;
import com.raizlabs.android.dbflow.StringUtils;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

public class UpdateInfoPresentImpl implements UpdateInfoPresent.Presenter {
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
        start();
        // 参数验证
        if (!checkInput(localPortraitPath) || !checkInput(description)) {
            my_view.UpdateInfoFail(R.string.data_account_update_invalid_parameter);
            return;
        }

        // 发起网络请求
        int sex = 0;
        if(isMale){
            sex = User.MALE;
        } else {
            sex = User.FEMALE;
        }
        UserHelper.update(new UserUpdateInfoModel(localPortraitPath, sex, description), new DataSource.Callback<UserIdentity>() {
            @Override
            public void onFail(int error) {
                if (my_view != null) {
                    Run.onUiAsync(new Action() {
                        @Override
                        public void call() {
                            my_view.UpdateInfoFail(error);
                        }
                    });
                }
            }

            @Override
            public void onSuccess(UserIdentity userIdentity) {
                if (my_view != null) {
                    Run.onUiAsync(new Action() {
                        @Override
                        public void call() {
                            my_view.UpdateInfoSuccess();
                        }
                    });
                }
            }
        });
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
