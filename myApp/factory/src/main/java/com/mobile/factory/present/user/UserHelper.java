package com.mobile.factory.present.user;

import com.mobile.factory.Factory;
import com.mobile.factory.R;
import com.mobile.factory.helper.network.CallRemote;
import com.mobile.factory.helper.network.NetworkHelper;
import com.mobile.util.StaticData.AccountData;
import com.mobile.util.data.DataSource;
import com.mobile.util.model.api.ResponseModel;
import com.mobile.util.model.api.account.AccountResponseModel;
import com.mobile.util.model.api.user.UserUpdateInfoModel;
import com.mobile.util.model.db.entity.User;
import com.mobile.util.model.db.identity.UserIdentity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 封装用户请求的类
 */
public class UserHelper {
    public static void update(UserUpdateInfoModel userUpdateInfoModel,
                              DataSource.Callback<UserIdentity> callback) {
        CallRemote callRemote = NetworkHelper.getRetrofit().create(CallRemote.class);
        Call<ResponseModel<UserIdentity>> responseModelCall = callRemote.updateUserInfo(userUpdateInfoModel, AccountData.getToken());
        request(responseModelCall, callback);
    }

    /**
     * 封装的请求方法
     * @param responseModelCall
     * @param callback
     */
    public static void request(Call<ResponseModel<UserIdentity>> responseModelCall,
                               DataSource.Callback<UserIdentity> callback){
        // 发起请求
        responseModelCall.enqueue(new Callback<ResponseModel<UserIdentity>>() {
            @Override
            public void onResponse(Call<ResponseModel<UserIdentity>> call,
                                   Response<ResponseModel<UserIdentity>> response) {
                // 拿response的body
                ResponseModel<UserIdentity> responseModel = response.body();
                if (responseModel.getCode() == 1) {
                    // 如果是个成功的请求
                    UserIdentity userIdentity = responseModel.getResponse_result();
                    User user = userIdentity.build();
                    // 拿用户并写入客户端的数据库
                    user.save();
                    callback.onSuccess(userIdentity);
                } else {
                    // 如果失败了，处理失败的提示
                    int error = Factory.transferResponseErrorCode(responseModel);
                    callback.onFail(error);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<UserIdentity>> call, Throwable t) {
                callback.onFail(R.string.data_network_error);
            }
        });
    }
}
