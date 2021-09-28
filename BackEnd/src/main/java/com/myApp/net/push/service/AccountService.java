package com.myApp.net.push.service;

import com.myApp.net.push.db.entity.User;
import com.myApp.net.push.db.mapper.UserMapper;
import com.myApp.net.push.identity.UserIdentity;
import com.myApp.net.push.model.account.LoginModel;
import com.myApp.net.push.model.response.AccountResponse;
import com.myApp.net.push.model.account.RegisterModel;
import com.myApp.net.push.model.response.Response;
import com.mysql.cj.util.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

// http://localhost:8080/api/account
@Path("/account")
public class AccountService {
    /**
     * @param registerModel 请求参数：pwd，name，accountNo, (pushId)
     * @return
     */
    @POST
    @Path("/register")
    // 指定请求与返回的相应体为JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<AccountResponse> register(RegisterModel registerModel) {
        // 校验入参非空
        if (!RegisterModel.isValid(registerModel)) {
            return Response.buildParameterError();
        }
        // 如果name或者accountno重复，告知用户
        if (UserMapper.findUserByPhone(registerModel.getAccountNo()) != null) {
            return Response.buildHaveAccountError();
        }
        if (UserMapper.findUserByName(registerModel.getName()) != null) {
            return Response.buildHaveNameError();
        }

        // 注册
        User user = UserMapper.addUser(registerModel.getAccountNo(),
                registerModel.getPwd(), registerModel.getName());
        if (user != null) {
            // 如果携带绑定参数，绑定
            if(!StringUtils.isNullOrEmpty(registerModel.getPushId())){
                return bind(user, registerModel.getPushId());
            }
            AccountResponse accountResponse = new AccountResponse(user);
            return Response.buildOk(accountResponse);
        }
        return Response.buildRegisterError();
    }

    /**
     * 登陆
     *
     * @param loginModel 请求参数：accountNo, pwd, (pushId)
     * @return
     */
    @Path("/login")
    @POST
    public Response<AccountResponse> login(LoginModel loginModel) {
        if(!LoginModel.isValid(loginModel)){
            return Response.buildParameterError();
        }

        User user = UserMapper.findUserByAccountAndPwd(loginModel.getAccountNo(),
                loginModel.getPwd());
        if(user != null){
            // 如果携带了pushID，绑定操作
            if(!StringUtils.isNullOrEmpty(loginModel.getPushId())){
                return bind(user, loginModel.getPushId());
            }
            AccountResponse accountResponse = new AccountResponse(user);
            return Response.buildOk(accountResponse);
        }
        return Response.buildLoginError();
    }

    /**
     * 绑定请求
     * @param pushId 设备id  放在url
     * @param token 用户token  放在head
     * @return
     */
    @Path("/bind/{pushId}")
    @POST
    public Response<AccountResponse> login(@PathParam("pushId") String pushId,
                                           @HeaderParam("token") String token) {
       if(!StringUtils.isNullOrEmpty(pushId) && !StringUtils.isNullOrEmpty(token)){
           User user = UserMapper.findUserByToken(token);
           if(user != null){
               return bind(user, pushId);
           }
           return Response.buildNoPermissionError();
       }
       return Response.buildParameterError();
    }

    /**
     * 绑定设备的操作
     * @param user 需要绑定的user
     * @param pushId 设备id
     * @return
     */
    public static Response<AccountResponse> bind(User user, String pushId){
        User bindUser = UserMapper.bindService(user, pushId);
        if(bindUser != null){
            AccountResponse accountResponse = new AccountResponse(bindUser);
            return Response.buildOk(accountResponse);
        }
        return Response.buildBindServiceError();
    }
}
