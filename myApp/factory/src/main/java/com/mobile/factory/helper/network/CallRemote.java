package com.mobile.factory.helper.network;

import com.mobile.util.model.api.ResponseModel;
import com.mobile.util.model.api.account.AccountResponseModel;
import com.mobile.util.model.api.account.LoginModel;
import com.mobile.util.model.api.account.RegisterModel;
import com.mobile.util.model.api.user.UserUpdateInfoModel;
import com.mobile.util.model.db.identity.UserIdentity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

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

    /**
     * 登录请求
     *
     * @param loginModel 传入一个loginModel
     * @return 返回一个ResponseModel<AccountResponseModel>
     */
    @POST("account/login")
    Call<ResponseModel<AccountResponseModel>> login(@Body LoginModel loginModel);

    /**
     * 更新用户信息请求
     * @param userUpdateInfoModel
     * @param token token要设置在header里
     * @return
     */
    @PUT("user/updateInfo")
    Call<ResponseModel<UserIdentity>> updateUserInfo(@Body UserUpdateInfoModel userUpdateInfoModel,
                                            @Header("token") String token);

}
