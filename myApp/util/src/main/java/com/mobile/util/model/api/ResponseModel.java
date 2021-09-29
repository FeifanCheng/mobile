package com.mobile.util.model.api;

import com.google.gson.annotations.Expose;

import java.util.Date;


/**
 * 对请求返回的封装
 */
public class ResponseModel<T> {
    public static final int SUCCESS = 1;
    public static final int ERROR_UNDEFINED = 0;

    public static final int ERROR_USER_NOT_FOUND = 4040;
    public static final int ERROR_GROUP_NOT_FOUND= 4041;
    public static final int ERROR_GROUP_MEMBER_NOT_FOUND= 4042;

    public static final int ERROR_CREATE_USER = 3000;
    public static final int ERROR_CREATE_GROUP = 3001;
    public static final int ERROR_CREATE_GROUP_MEMBER = 3002;

    public static final int ERROR_REQUEST_PARAMETERS = 4000;
    public static final int ERROR_REQUEST_PARAMETERS_ACCOUNT_EXIST = 4001;
    public static final int ERROR_REQUEST_PARAMETERS_NAME_EXIST = 4002;

    public static final int ERROR_BACKEND_SERVICE = 5000;

    public static final int ERROR_WRONG_ACCOUNT_TOKEN = 2000;
    public static final int ERROR_ACCOUNT_LOGIN_FAIL = 2001;
    public static final int ERROR_ACCOUNT_REGISTER_FAIL = 2002;
    public static final int ERROR_ACCOUNT_NO_PERMISSION = 2010;
    public static final int ERROR_ACCOUNT_BIND_SERVICE_FAIL = 2011;

    public static final int ERROR_USER_UPDATE_INFO_FAIL = 6000;

    @Expose
    private int code;
    @Expose
    private String message;
    @Expose
    private Date time;
    @Expose
    private T response_result;

    public ResponseModel() {
        code = SUCCESS;
        message = "Success";
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public T getResponse_result() {
        return response_result;
    }

    public void setResponse_result(T response_result) {
        this.response_result = response_result;
    }
}
