package com.mobile.factory.helper.network;

import com.mobile.util.model.api.ResponseModel;
import com.mobile.util.model.api.account.AccountResponseModel;
import com.mobile.util.model.api.account.RegisterModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 请求
 */
public interface CallRemote {
    /**
     * 注册请求
     *
     * @param registerModel 传入一个registerModel
     * @return 返回一个ResponseModel<AccountResponseModel>
     */
    @POST("account/register")
    Call<ResponseModel<AccountResponseModel>> register(@Body RegisterModel registerModel);

}
