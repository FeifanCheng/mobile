package com.mobile.factory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobile.util.StaticData.AccountData;
import com.mobile.util.app.Application;
import com.mobile.util.model.api.ResponseModel;
import com.mobile.util.model.db.DBExclusionStrategy;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Factory {
    // 单例模式
    private static final Factory factory = new Factory();
    // 全局的线程池
    private final Executor executor;
    // 全局的Gson
    private final Gson gson;


    private Factory() {
        executor = Executors.newFixedThreadPool(4);
        gson = new GsonBuilder()
                // 设置时间格式
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                // 数据库类不用gson
                .setExclusionStrategies(new DBExclusionStrategy())
                .create();
    }


    public static Factory getFactory() {
        return factory;
    }


    /**
     * 返回全局的Application
     *
     * @return Application
     */
    public static Application app() {
        return Application.getApp();
    }


    /**
     * 异步运行的方法
     *
     * @param runnable Runnable
     */
    public static void runOnAsync(Runnable runnable) {
        factory.executor.execute(runnable);
    }

    /**
     * 返回一个全局的Gson
     *
     * @return Gson
     */
    public static Gson getGson() {
        return factory.gson;
    }

    public static int transferResponseErrorCode(ResponseModel responseModel) {
        // 不处理空model和成功的
        if (responseModel != null && responseModel.getCode() != 1) {
            if (responseModel.getCode() == ResponseModel.ERROR_UNDEFINED) {
                return R.string.data_rsp_error_unknown;
            } else if (responseModel.getCode() == ResponseModel.ERROR_CREATE_USER) {
                return R.string.data_rsp_error_create_user;
            } else if (responseModel.getCode() == ResponseModel.ERROR_ACCOUNT_BIND_SERVICE_FAIL) {
                return R.string.data_rsp_error_bind_service;
            } else if (responseModel.getCode() == ResponseModel.ERROR_ACCOUNT_LOGIN_FAIL) {
                return R.string.data_rsp_error_account_login;
            } else if (responseModel.getCode() == ResponseModel.ERROR_CREATE_GROUP) {
                return R.string.data_rsp_error_create_group;
            } else if (responseModel.getCode() == ResponseModel.ERROR_ACCOUNT_NO_PERMISSION) {
                return R.string.data_rsp_error_account_no_permission;
            } else if (responseModel.getCode() == ResponseModel.ERROR_ACCOUNT_REGISTER_FAIL) {
                return R.string.data_rsp_error_account_register;
            } else if (responseModel.getCode() == ResponseModel.ERROR_BACKEND_SERVICE) {
                return R.string.data_rsp_error_service;
            } else if (responseModel.getCode() == ResponseModel.ERROR_WRONG_ACCOUNT_TOKEN) {
                return R.string.data_rsp_error_account_token;
            } else if (responseModel.getCode() == ResponseModel.ERROR_GROUP_MEMBER_NOT_FOUND) {
                return R.string.data_rsp_error_not_found_group_member;
            } else if (responseModel.getCode() == ResponseModel.ERROR_REQUEST_PARAMETERS_ACCOUNT_EXIST) {
                return R.string.data_rsp_error_parameters_exist_account;
            } else if (responseModel.getCode() == ResponseModel.ERROR_REQUEST_PARAMETERS_NAME_EXIST) {
                return R.string.data_rsp_error_parameters_exist_name;
            } else if (responseModel.getCode() == ResponseModel.ERROR_GROUP_NOT_FOUND) {
                return R.string.data_rsp_error_not_found_group;
            } else if (responseModel.getCode() == ResponseModel.ERROR_CREATE_GROUP_MEMBER) {
                return R.string.data_rsp_error_create_user;
            } else if (responseModel.getCode() == ResponseModel.ERROR_REQUEST_PARAMETERS) {
                return R.string.data_rsp_error_parameters;
            } else if (responseModel.getCode() == ResponseModel.ERROR_USER_UPDATE_INFO_FAIL) {
                return R.string.data_account_update_invalid_parameter;
            } else if (responseModel.getCode() == ResponseModel.ERROR_USER_NOT_FOUND) {
                return R.string.data_rsp_error_not_found_user;
            }
        }
        return -1;
    }


}

