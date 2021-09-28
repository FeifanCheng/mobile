package com.myApp.net.push.response;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 所有response的定义类
 * @param <T>
 */
public class Response<T> implements Serializable {
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
    private LocalDateTime time = LocalDateTime.now();
    @Expose
    private T response_result;

    public Response() {
        code = SUCCESS;
        message = "Success";
    }

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Response(T response_result) {
        this();
        this.response_result = response_result;
    }

    public Response(int code, String message, T response_result) {
        this.code = code;
        this.message = message;
        this.response_result = response_result;
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

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public T getResponse_result() {
        return response_result;
    }

    public void setResponse_result(T response_result) {
        this.response_result = response_result;
    }

    public static <M> Response<M> buildOk() {
        return new Response<M>();
    }

    public static <M> Response<M> buildOk(M result) {
        return new Response<M>(result);
    }

    public static <M> Response<M> buildParameterError() {
        return new Response<M>(ERROR_REQUEST_PARAMETERS, "Request Parameters Error.");
    }

    public static <M> Response<M> buildHaveAccountError() {
        return new Response<M>(ERROR_REQUEST_PARAMETERS_ACCOUNT_EXIST, "Already have this account.");
    }

    public static <M> Response<M> buildHaveNameError() {
        return new Response<M>(ERROR_REQUEST_PARAMETERS_NAME_EXIST, "Already have this name.");
    }

    public static <M> Response<M> buildServiceError() {
        return new Response<M>(ERROR_BACKEND_SERVICE, "Service Error.");
    }

    public static <M> Response<M> buildNotFoundUserError(String str) {
        return new Response<M>(ERROR_USER_NOT_FOUND, str != null ? str : "Not Found User.");
    }

    public static <M> Response<M> buildNotFoundGroupError(String str) {
        return new Response<M>(ERROR_GROUP_NOT_FOUND, str != null ? str : "Not Found Group.");
    }

    public static <M> Response<M> buildNotFoundGroupMemberError(String str) {
        return new Response<M>(ERROR_GROUP_MEMBER_NOT_FOUND, str != null ? str : "Not Found GroupMember.");
    }

    public static <M> Response<M> buildAccountError() {
        return new Response<M>(ERROR_WRONG_ACCOUNT_TOKEN, "Account Error; you need login.");
    }

    public static <M> Response<M> buildLoginError() {
        return new Response<M>(ERROR_ACCOUNT_LOGIN_FAIL, "Account or password error.");
    }

    public static <M> Response<M> buildRegisterError() {
        return new Response<M>(ERROR_ACCOUNT_REGISTER_FAIL, "Have this account.");
    }

    public static <M> Response<M> buildNoPermissionError() {
        return new Response<M>(ERROR_ACCOUNT_NO_PERMISSION, "You do not have permission to operate.");
    }

    public static <M> Response<M> buildCreateError(int type) {
        return new Response<M>(type, "Create failed.");
    }

    public static <M> Response<M> buildBindServiceError() {
        return new Response<M>(ERROR_ACCOUNT_BIND_SERVICE_FAIL, "Bind service failed.");
    }

    public static <M> Response<M> buildUserUpdateError() {
        return new Response<M>(ERROR_USER_UPDATE_INFO_FAIL, "Update user info failed.");
    }
}
