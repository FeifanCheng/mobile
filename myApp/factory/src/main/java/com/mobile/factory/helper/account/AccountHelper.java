package com.mobile.factory.helper.account;

import com.mobile.factory.Factory;
import com.mobile.factory.R;
import com.mobile.factory.helper.network.CallRemote;
import com.mobile.factory.helper.network.NetworkHelper;
import com.mobile.util.data.DataSource;
import com.mobile.util.model.api.ResponseModel;
import com.mobile.util.model.api.account.AccountResponseModel;
import com.mobile.util.model.api.account.RegisterModel;
import com.mobile.util.model.db.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 账户进行网络请求的类
 */
public class AccountHelper {
    /**
     * 封装注册请求
     *
     * @param registerModel 一个请求model
     * @param callback      处理请求结果的回调
     */
    public static void register(RegisterModel registerModel, DataSource.Callback<User> callback) {
        CallRemote callRemote = NetworkHelper.getRetrofit().create(CallRemote.class);
        Call<ResponseModel<AccountResponseModel>> responseModelCall = callRemote.register(registerModel);
        // 发起请求
        responseModelCall.enqueue(new Callback<ResponseModel<AccountResponseModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<AccountResponseModel>> call,
                                   Response<ResponseModel<AccountResponseModel>> response) {
                // 拿response的body
                ResponseModel<AccountResponseModel> responseModel = response.body();
                if (responseModel.getCode() == 1) {
                    // 如果是个成功的请求
                    AccountResponseModel responseResult = responseModel.getResponse_result();
                    User user = responseResult.getUser();
                    if (responseResult.isBindService()) {
                        // 如果已经绑定，直接返回即可
                        // TODO：拿用户并写入客户端的数据库

                        callback.onSuccess(user);
                    } else {
                        // TODO：暂时跳转，要绑定设备的（没做推送。。太麻烦了）
                        callback.onSuccess(user);
                    }

                } else {
                    // 如果失败了，处理失败的提示
                    int error = Factory.transferResponseErrorCode(responseModel);
                    callback.onFail(error);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<AccountResponseModel>> call, Throwable t) {
                callback.onFail(R.string.data_network_error);
            }
        });
    }
}
